(ns quil-midi.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil-midi.midi :as midi]))

(declare c-r log-msg)

(def state (atom 0))

(defn log-msg [msg]
  (print (str "Message recieved" (:velocity msg) "\n")))

(defn update-atom [midi-msg]
  (let [v :velocity :as midi-msg]))
(midi/listener log-msg)

(defn setup []
  (q/background 0)
  (q/frame-rate 10))

(defn draw []
  (print @state)
  (q/background @state))

(q/defsketch quil-midi
  :size [100, 100]
  :setup setup
  :draw draw)

;; util

(defn c-r [x]
  (x * 255) / 127)
