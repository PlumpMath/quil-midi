(ns quil-midi.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil-midi.midi :as midi]))

(declare c-r)

(def state (atom 0))

(midi/listener (fn [msg]
                 (swap! state (fn [x]
                                (c-r (:velocity msg))))))

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
