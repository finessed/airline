(ns airline.data.airports
  (:import  [java.io File])
  (:use     [clojure-csv.core :only [parse-csv]])
  (:require [clj-time.core :as t]))

(defn airport-to-utc-offset [^File in]
  "Make a map of 3-digit IATA code to hours offset from UTC.
   e.g. {\"LHR\" 0.0}"
  (with-open [rdr (clojure.java.io/reader in)]
    (reduce
      (fn [out row]
        (let [[_ _ _ _ iata _ _ _ _ offset & r] row]
          (assoc out iata (Double. offset))))
      {}
      (doall (parse-csv rdr)))))

(defn offset-to-tz [airports]
  (into {}
    (for [[airport utc-offset] airports]
      [airport (t/time-zone-for-offset utc-offset)])))

(defn airport-to-tz [^File in]
  (->
    (airport-to-utc-offset in)
    (offset-to-tz)))
