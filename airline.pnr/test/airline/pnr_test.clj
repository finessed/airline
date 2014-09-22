(ns airline.pnr-test
  (:require [clojure.test :refer :all]
            [airline.pnr :refer :all]))

(deftest test-new-pnr
  (testing "PNR number is correct format"
    (is (= 6 (count (new-pnr)))))
  (testing "PNR numbers do not repeat"
    (let [pnr-1 (new-pnr)
          pnr-2 (new-pnr)]
      (is (not= pnr-1 pnr-2 )))))


