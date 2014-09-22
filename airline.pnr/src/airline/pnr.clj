(ns airline.pnr
   (:require [clojure.java.jdbc :as rdbms])
   (:import [java.util Random]))


; TODO - could use a better RNG or a better seed!
(def rnd (Random.))

(defn ^String new-pnr []
  "Generate a new PNR number using standard
   Java RNG initialized by system clock."
  (Integer/toString 
    (.nextInt rnd) 36))
    ;TODO avoid negative (.nextInt rnd Integer/MAX_VALUE) 36))