(ns airport.locator.data
  (:use [clojure-csv.core :only [parse-csv]]))

(defn- parse-location-row [^String row]
  "Extract airport code, latitude and longitude"
  (let [[^String iata-3 ^String latitude ^String longitude] row]
    [iata-3 [(Double/parseDouble latitude) (Double/parseDouble longitude)]]))

(defn- read-airports [in]
  "Returns a seq of pairs [airport-code, location]"
  (with-open [rdr (clojure.java.io/reader in)]
    (doall
      (map parse-location-row (parse-csv rdr)))))

(defn airport-locations []
  "Return a map of airport-code:location"
  (->>
    (clojure.java.io/resource "org/openflights/airport-locations.csv")
    (read-airports)
    (reduce conj (sorted-map))))
