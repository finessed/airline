(defproject airport.locator "0.7.0"
  :description "A RESTful micro-service for locating airports."
  :url "https://github.com/devstopfix/airline/airport.locator"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.9"]
                 [clojure-csv/clojure-csv "2.0.1"]
                 [ring.middleware.jsonp "0.1.6"]
                 [ring/ring-defaults "0.1.2"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-jetty-adapter "1.3.1"]
                 [ring.middleware.cache-control "0.1.0"]
                 [cheshire "5.3.1"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler airport.locator.handler/app}
  :main airport.locator.handler
  :aot [airport.locator.handler]
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
