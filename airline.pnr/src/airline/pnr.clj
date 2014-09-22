(ns airline.pnr
   (:require [clojure.java.jdbc :as rdbms])
   (:import [java.util Random]))


; TODO - could use a better RNG or a better seed!
(def std-rnd (Random.))

(defn ^String new-pnr 
  "Generate a new 6-digit alpha-numeric PNR number."
   ([] (new-pnr std-rnd))
   ([^Random rnd]
    (->>
      (take 6 (repeatedly #(.nextInt rnd 36)))
      (map #(Integer/toString % 36))
      (apply str)
      (.toUpperCase))))
