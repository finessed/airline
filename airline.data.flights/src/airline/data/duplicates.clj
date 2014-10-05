(ns airline.data.duplicates
  (:require [clojure.core.cache :as cache]))

; Our target database has a unique constraint
; of [date, flight], so drop any duplicates from
; the timetable. The timetable can contain duplicate
; data with English descriptions (e.g. Autumn only)
; which cannot be simply understood by this library.


(def flight-cache "Keep a list of recently seen flight keys"
  (atom (cache/fifo-cache-factory {} :threshold 1000)))

(defn duplicate? [row]
  (let [[airport _ _ _ flight day] row
        cache-key [day airport flight]]
    (if (cache/has? @flight-cache cache-key)
      true
      (do
        (swap! flight-cache assoc cache-key true)
        false))))
