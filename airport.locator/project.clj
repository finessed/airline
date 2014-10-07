(defproject airport.locator "0.2.0"
  :description "A RESTful micro-service for locating airports."
  :url "https://github.com/devstopfix/airline/airport.locator"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.9"]
                 [clojure-csv/clojure-csv "2.0.1"]
                 [ring/ring-json "0.3.1"]
                 [cheshire "5.3.1"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler airport.locator.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
