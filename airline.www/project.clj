(defproject airline.www "0.1.0"
  :description "Web application for ficticious airline."
  :url "https://github.com/devstopfix/airline/airline.www"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.9"]
                 [hiccup "1.0.5"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler airline.www.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
