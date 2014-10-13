(ns airport.locator.handler
  (:require [airport.locator.service :as srvc]
            [compojure.core :refer :all]
            [ring.middleware.defaults :only [api-defaults]]
            [compojure.route :as route]
            [ring.util.response :as res])
  (:use [ring.middleware.json :only [wrap-json-response]]
        [ring.middleware.jsonp :only [wrap-json-with-padding]]
        [ring.middleware.params :only [wrap-params]]
        [ring.adapter.jetty :only [run-jetty]]
        [ring.middleware.cache-control :only [cache-control-max-age]])
  (:gen-class))

(def resource-description
  "URL structure:\n
    /airport/XXX\n
    /route/XXX,YYY[,ZZZ]...\n\n
  where XXX are valid IATA-3 airport codes.\n")

(defroutes app-routes
  (GET ["/geo/airport/:code" :code srvc/re-iata-3] [code]
    (srvc/airport-location code))
  (GET ["/geo/airport/:code/destinations" :code srvc/re-iata-3] [code]
    (srvc/airport-destinations code))
  (GET ["/geo/route/:stops" :stops #"[A-Z]{3}(,[A-Z]{3})+"] [stops]
    (srvc/airport-route stops))
  (ANY "*" [] (res/status
                (res/response
                  (str "400 Bad Request\n" resource-description))
                400)))

(def cache-control
  { 200 (* 60 60 24)
    400 (* 60 60 24 30)
    404 (* 60 60)})

(def app
  (-> app-routes
    (wrap-json-response)
    (wrap-json-with-padding)
    (wrap-params)
    (cache-control-max-age cache-control)))

(defn start-server [port]
  (run-jetty app {:port port :join? false}))

(defn -main [port & args]
  (start-server (Integer/parseInt port)))