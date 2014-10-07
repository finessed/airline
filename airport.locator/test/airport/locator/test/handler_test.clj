(ns airport.locator.test.handler-test
  (:require [clojure.test :refer :all]
            [airport.locator.handler :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]))

(deftest test-app
  (testing "airport"
    (let [response (app (mock/request :get "/airport/LHR"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))
      (is (.contains (get-in response [:headers "Cache-Control"]) "max-age="))
      (is (= (json/parse-string (:body response))
            {"LHR" [51.477500 -0.461389]})))

  (testing "unknown airport has nice error message"
    (let [response (app (mock/request :get "/airport/NIN"))]
      (is (= (:status response) 404))
      (is (.contains (get-in response [:headers "Cache-Control"]) "max-age=360"))
      (is (.contains (:body response) "IATA-3 code 'NIN"))))

  (testing "lower case airport fails"
    (let [response (app (mock/request :get "/airport/lhr"))]
      (is (.contains (get-in response [:headers "Cache-Control"]) "max-age="))
      (is (= (:status response) 400))))

  (testing "lower missing airport fails"
    (let [response (app (mock/request :get "/airport/"))]
      (is (= (:status response) 400))))

  (testing "routes route"
    (let [response (app (mock/request :get "/route/LHR,CPH,BLL"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))
      (is (.contains (get-in response [:headers "Cache-Control"]) "max-age="))
      (is (= (json/parse-string (:body response))
        {"route" [
          [{"LHR" [51.477500 -0.461389]} {"CPH" [55.617917 12.655972]}]
          [{"CPH" [55.617917 12.655972]} {"BLL" [55.740322 9.151778]}]]}))))

  (testing "routes need more than one airport"
    (let [response (app (mock/request :get "/route/LHR"))]
      (is (= (:status response) 400)))
    (let [response (app (mock/request :get "/route/LHR,"))]
      (is (= (:status response) 400))))

  (testing "routes accept two or more airports"
    (let [response (app (mock/request :get "/route/LHR,CPH"))]
      (is (.contains (get-in response [:headers "Cache-Control"]) "max-age="))
      (is (= (:status response) 200)))
    (let [response (app (mock/request :get "/route/LHR,CPH,BLL"))]
      (is (= (:status response) 200))))

  (testing "routes with unknown airport has nice error message"
    (let [response (app (mock/request :get "/route/LHR,NIN,BLL"))]
      (is (= (:status response) 404))
      (is (.contains (:body response) "IATA-3 code 'NIN"))))

  (testing "any other route is bad"
    (let [response (app (mock/request :get "/oops"))]
      (is (= (:status response) 400))))))
