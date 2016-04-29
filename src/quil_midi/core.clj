(ns quil-midi.core
  (:require
   [clojure.core.match :refer [match]]
   [quil.core :as q]
   [clojure.core.async :refer [sliding-buffer chan go <! <!! >! >!!] ]
   [quil.middleware :as m]
   [quil-midi.midi :as midi])
  (:import [processing.video Capture]))

(declare midi-to-255 midi-chan setup draw midi-to-height)
(def midi-chan (midi/listener))

(defn setup []
  (q/frame-rate 10)
  0)


(defn update-state [state]
  (midi-to-255 (:velocity (<!! midi/sliding-chan)))
  )

(defn draw-state [state]
  (q/background state)
  )

(q/defsketch quil-midi
  :size [400 400]
  :middleware [m/fun-mode]
  :setup setup
  :update update-state
  :draw draw-state)

;; util
(defn midi-to-255 [x]
  (/ (* x 255) 127))

(defn midi-to-height [x]
  (/ (* x (q/height)) 127))

;; main
(defn -main [] (print "loaded"))
