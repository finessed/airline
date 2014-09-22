(ns airline.pnr-test
  (:require [clojure.test :refer :all]
            [airline.pnr :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :as ct :refer (defspec)]))

(defn is-valid? [^String pnr]
  (.matches pnr "^[0-9A-Z]{6}$"))

(deftest test-new-pnr
  (testing "PNR number is correct format"
    (is (is-valid? (new-pnr)))))

; Test all PNRs generated are of valid format
(defspec test-pnr-format
  999999
  (prop/for-all [a gen/int] 
    (is-valid? (new-pnr (java.util.Random. a)))))

; Test we don't get any duplicates in small runs
(defspec test-pnr-does-not-repeat
  1000
  (prop/for-all [seed gen/int] 
    (let [rnd         (java.util.Random. seed)
          sample-size 1000
          sample      (take sample-size (repeatedly #(new-pnr rnd)))]
      (= sample-size (count (set sample))))))
