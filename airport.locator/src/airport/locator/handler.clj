(ns airport.locator.handler
  (:require [airport.locator.service :as srvc]
            [compojure.core :refer :all]
            [ring.middleware.defaults :only [api-defaults]]
            [compojure.route :as route]
            [ring.util.response :as res])
  (:use [ring.middleware.json :only [wrap-json-response]]
        [ring.middleware.jsonp :only [wrap-json-with-padding]]
        [ring.middleware.params :only [wrap-params]]
        [ring.middleware.keyword-params :only [wrap-keyword-params]]
        [ring.adapter.jetty :only [run-jetty]])
  (:gen-class))

(def resource-description
  "URL structure:\n
    /airport/XXX\n
    /route/XXX,YYY[,ZZZ]...\n\n
  where XXX are valid IATA-3 airport codes.\n")

(def bad-request
  (res/status
    (res/response (str "400 Bad Request\n" resource-description))
    400))

(defroutes app-routes
  (GET ["/geo/airport/:code" :code srvc/re-iata-3] [code]
    (srvc/airport-location code))
  (GET ["/geo/route/:stops" :stops #"[A-Z]{3}(,[A-Z]{3})+"] [stops]
    (srvc/airport-route stops))
  (ANY "*" [] bad-request))

; TODO extract this into a library
; TODO if the response already has Cache-Control or Expires, do nothing!
(defn- cache-directive [handler]
  (let [ages {200 (* 60 60 24)
              400 (* 60 60 24)
              404 (* 60 6) ; TODO check HTTP cache rules for 4xx errors
              410 (* 60 60 24)}]
    (fn [request]
      (let [response (handler request)]
        (res/header response
          "Cache-Control"
          (format "max-age=%d"
            (get ages (get response :status 60))))))))

; TODO define this with thread macro!
(def app
  (cache-directive
    (wrap-params
      (wrap-keyword-params
        (wrap-json-with-padding
          (wrap-json-response
            app-routes))))))

(defn start-server [port]
  (run-jetty app {:port port :join? false}))

(defn -main [port & args]
  (start-server (Integer/parseInt port)))