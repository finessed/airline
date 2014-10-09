(ns airport.locator.service
  (:require [airport.locator.data :as data]
            [ring.util.mime-type :as mime]
            [ring.util.response :as res]
            [cheshire.core :as json]))

; An IATA airport code is a three-letter code designating many airports around the world,
; defined by the International Air Transport Association (IATA)
; http://en.wikipedia.org/wiki/International_Air_Transport_Association_airport_code
(def re-iata-3 #"[A-Z]{3}")

; Map of airport code to Vector of Latitude, Longtitude.
(def airports (data/airport-locations))

(defn- msg-404 [code]
  (format "Not found: IATA-3 code '%s'\n" code))

; Airports

(defn airport-location [code]
  (if-let [location (get airports code)]
    (let [[latitude longitude & r] location]
      (res/response
        {"airport" code
         "loc" [latitude longitude]}))
    (res/not-found
      (msg-404 code))))

; Routes

(defn- route-hop [hop]
  "Convert a list of pairs into a map of airport code:location."
  (let [[dept dest] hop
        dept-loc (get airports dept)
        dest-loc (get airports dest)]
    [{"dept" dept "dept-loc" dept-loc}
     {"dest" dest "dest-loc" dest-loc}]))

(defn- route-hops [route]
  (->>
    (partition 2 1 route)
    (map route-hop)
    (assoc {} "route")))

(defn- unknown-location? [route]
  "Find first airport code for which we don't have a location"
  (first
    (remove #(contains? airports %) route)))

(defn airport-route [^String route]
  (let [route (take 16 (.split route ","))]
    (if-let [code (unknown-location? route)]
      (res/not-found (msg-404 code))
      (res/response (route-hops route)))))
