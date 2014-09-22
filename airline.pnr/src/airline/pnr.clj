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

(defn write-pnr [db ^String pnr ^Long booking-id]
  (rdbms/insert! 
    db "PNRNum" {"PNR" pnr, "Booking_ID" booking-id}))

; TODO check for collision and retry
(defn claim-pnr [db ^Long booking-id]
  "Generate a new PNR number for the given booking,
   write it to the database and return the PNR"
  (let [pnr (new-pnr)]
    (write-pnr db pnr booking-id)
    pnr))