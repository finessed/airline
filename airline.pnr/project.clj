(defproject airline.pnr "0.1.1"
  :description "Generate unique PNR numbers."
  :url "https://github.com/devstopfix/airline/airline.pnr"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
    :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/java.jdbc "0.3.4"]
                 [postgresql "9.1-901-1.jdbc4"]
                 [org.clojure/test.check "0.5.9"]])