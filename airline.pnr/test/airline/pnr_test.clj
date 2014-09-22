(ns airline.pnr-test
  (:require [clojure.test :refer :all]
            [airline.pnr :refer :all]))

(defn is-valid? [^String pnr]
  (.matches pnr "^[0-9A-Z]{6}$"))

(deftest test-new-pnr
  (testing "PNR number is correct format"
    (is (is-valid? (new-pnr))))
  (testing "PNR numbers do not repeat"
    (let [pnr-1 (new-pnr)
          pnr-2 (new-pnr)]
      (is (is-valid? pnr-1))
      (is (is-valid? pnr-2))
      (is (not= pnr-1 pnr-2 )))))


