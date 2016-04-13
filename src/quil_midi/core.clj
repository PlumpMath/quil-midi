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

(midi/listener dispatch-midi-event)

(defn setup []
  (q/frame-rate 10))

(defn draw []
  (def h (q/random 0 400))
  (q/background (midi-to-255 (:val @cc0)) (midi-to-255 (:val @cc1)) (midi-to-255 (:val @cc2)))
  (q/stroke 255)
  (q/stroke-weight 10)
  (q/line 0 (midi-to-height (:val @cc2))  800 (midi-to-height (:val @cc0)))
  (q/line 0 (midi-to-height (:val @cc1))  800 (midi-to-height (:val @cc1)))
  (q/line 0 (midi-to-height (:val @cc0))  800 (midi-to-height (:val @cc2)))
  (q/stroke-weight 2)
  (q/stroke 0)
  ;;(q/line (q/random 0 (q/height)) (q/random 0 (q/height)) (q/random 0 (q/width)) (q/random 0 (q/width)))
  )

(q/defsketch quil-midi
  :size [800, 400]
  :setup setup
  :features [:keep-on-top]
  :renderer :p3d
  :draw draw)

;; util

(defn midi-to-255 [x]
  (/ (* x 255) 127))

(defn midi-to-height [x]
  (/ (* x (q/height)) 127))

;; main

(defn -main [] (print "loaded"))
