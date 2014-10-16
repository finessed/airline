(ns airport.locator.test.handler-test
  (:require [clojure.test :refer :all]
            [airport.locator.handler :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]))

(defn has-max-age-cache-control
  ([response]
    (->>
      (get-in response [:headers "Cache-Control"] "")
      (re-matches #"max-age=(\d+)" )
      (last)))
  ([response expected]
    (let [age (Integer/parseInt (has-max-age-cache-control response))]
      (and
        (>= age (* expected 0.6 ))
        (<= age (* expected 1.4 ))))))

(deftest test-app
  (testing "airport"
    (let [response (app (mock/request :get "/geo/airport/LHR"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))
      (is (has-max-age-cache-control response))
      (is (= (json/parse-string (:body response))
            {"airport" "LHR"
             "location" [51.477500 -0.461389]}))))

  (testing "unknown airport has nice error message"
    (let [response (app (mock/request :get "/geo/airport/NIN"))]
      (is (= (:status response) 404))
      (is (has-max-age-cache-control response 3600))
      (is (.contains (:body response) "IATA-3 code 'NIN"))))

  (testing "lower case airport fails"
    (let [response (app (mock/request :get "/geo/airport/lhr"))]
      (is (has-max-age-cache-control response))
      (is (= (:status response) 400))))

  (testing "lower missing airport fails"
    (let [response (app (mock/request :get "/geo/airport/"))]
      (is (= (:status response) 400))))

  (testing "routes route"
    (let [response (app (mock/request :get "/geo/route/LHR,CPH,BLL"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))
      (is (has-max-age-cache-control response))
      (is (= (json/parse-string (:body response))
            {"route" [
                       [{"dept" "LHR" "dept-loc" [51.477500 -0.461389]} {"dest" "CPH" "dest-loc" [55.617917 12.655972]}]
                       [{"dept" "CPH" "dept-loc" [55.617917 12.655972]} {"dest" "BLL" "dest-loc" [55.740322 9.151778]}]]}))))

  (testing "routes need more than one airport"
    (let [response (app (mock/request :get "/geo/route/LHR"))]
      (is (= (:status response) 400)))
    (let [response (app (mock/request :get "/geo/route/LHR,"))]
      (is (= (:status response) 400))))

  (testing "routes accept two or more airports"
    (let [response (app (mock/request :get "/geo/route/LHR,CPH"))]
      (is (has-max-age-cache-control response))
      (is (= (:status response) 200)))
    (let [response (app (mock/request :get "/geo/route/LHR,CPH,BLL"))]
      (is (= (:status response) 200))))

  (testing "routes with unknown airport has nice error message"
    (let [response (app (mock/request :get "/geo/route/LHR,NIN,BLL"))]
      (is (= (:status response) 404))
      (is (.contains (:body response) "IATA-3 code 'NIN")))))

(deftest test-routes
  (testing "any other route is bad"
    (let [response (app (mock/request :get "/geo/oops"))]
      (is (= (:status response) 400)))))

(defn ^String body-str [response]
  "Read the body of the response as a String."
  (with-open [rdr
              (clojure.java.io/reader (:body response))]
    (slurp rdr)))

(deftest test-jsonp
  (testing "jsonp wrapping"
    (let [response (app (mock/request :get "/geo/airport/LHR" {:callback "myf"}))
          body (body-str response)]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/javascript; charset=utf-8"))
      (is (has-max-age-cache-control response))
      (is (.startsWith body "myf(") body)
      (is (.endsWith body ");") body)
      (is (= (json/parse-string
               (second
                 (clojure.string/split body #"[/(/)]")))
            {"airport" "LHR"
             "location" [51.477500 -0.461389]})))))

(deftest test-destinations
  (testing "airport"
    (let [response (app (mock/request :get "/geo/airport/BLL/destinations"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))
      (is (has-max-age-cache-control response))
      (is (= (json/parse-string (:body response))
            {"airport" "BLL",
             "location" [55.740322 9.151778],
             "destinations"
             [{"airport" "FRA", "location" [50.026421 8.543125]}
              {"airport" "MUC", "location" [48.353783 11.786086]}]})))))
