(ns quil-midi.core
  (:require
   [clojure.core.match :refer [match]]
   [quil.core :as q]
   [quil.middleware :as m]
   [quil-midi.midi :as midi])
  (:import [processing.video Capture]))

(declare midi-to-255 log-msg update-atom midi-to-height)

(def cc0 (atom {:val 0}))
(def cc1 (atom {:val 0}))
(def cc2 (atom {:val 0}))
(def camera (atom {:instance nil}))

;; DISPATCH
(defn dispatch-midi-event
  [midi-msg]
  (let [{n :note v :velocity :as msg} midi-msg]
    (match n
           0 (update-atom v cc0)
           1 (update-atom v cc1)
           2 (update-atom v cc2))))


(defn update-atom [val state]
    (if (not= val (:val @state))
      (swap! state assoc :val val)
      nil))

;;(midi/listener dispatch-midi-event)

(defn setup []
  (q/frame-rate 10))

(defn draw []
  (q/background 100)
  (doseq [x (range 0 (q/width))
          y (range 0 (q/width))]
    (q/stroke (q/random 0 255))
    (q/point x y)
  )
)

(q/defsketch quil-midi
  :size [1000 800]
  :setup setup
  :renderer :p3d
  :draw draw)

;; util

(defn midi-to-255 [x]
  (/ (* x 255) 127))

(defn midi-to-height [x]
  (/ (* x (q/height)) 127))

;; main

(defn -main [] (print "loaded"))
