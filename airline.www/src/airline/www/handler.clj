(ns airline.www.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [airline.www.html :as html]))

(defroutes app-routes
  (GET "/" [] (html/home-page))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
