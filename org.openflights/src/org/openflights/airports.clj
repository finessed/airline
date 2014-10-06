(ns org.openflights.airports
  (:import [java.io File])
  (:use [clojure-csv.core :only [parse-csv write-csv]]))


(defn extract-position [row]
  (let [[_ name city country iata-3 icao-4 latitude longitude & r] row]
    [iata-3 latitude longitude]))

(defn extract-positions [^File in]
  "Extract airport code and latitute, longitude
   from openflights.org airports.dat. No header
   row is expected. Writes to STDOUT."
    (with-open [rdr (clojure.java.io/reader in)]
      (doseq [line (parse-csv rdr)]
        (let [row (extract-position line)]
          (when (re-matches #"[A-Z]{3}" (first row))
            (->> (list row)
              (write-csv)
              (.write *out*)))))))

(defn run-extract-positions [& args]
  (extract-positions
    (File. (first args))))