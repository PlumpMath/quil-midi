(ns quil-midi.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil-midi.midi :as midi]))

(declare c-r log-msg)

(def state (atom {:val 0}))

(defn log-msg [msg]
  (print (str "_ " (:velocity msg) (type (:velocity msg)) "\n")))

(defn update-atom [midi-msg]
  (let [{v :velocity :as msg} midi-msg]
    (if (not= v (:val @state))
       (swap! state assoc :val v))
      nil))

(midi/listener update-atom)

(defn setup []
  (q/background 0)
  (q/frame-rate 10))

(defn draw []
  (q/background (c-r (:val @state)))
  (q/stroke 255 0 0)
  (q/stroke-weight 5)
  (q/line 0 0  (/ (* (:val @state) 400) 255) (/ (* (:val @state) 400) 255)))

(q/defsketch quil-midi
  :size [400, 400]
  :setup setup
  :draw draw)

;; util

(defn c-r [x]
  (/ (* x 255) 127))

;; main

(defn -main [] (print "loaded"))
