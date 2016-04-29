(ns quil-midi.core
  (:require
   [quil.core :as q]
   [clojure.core.async :refer [sliding-buffer chan go <!! >!! alt!! alts!! timeout]]
   [quil.middleware :as m]
   [quil-midi.midi :as midi])
  (:import [processing.video Capture]))

(declare midi-to-255 midi-chan setup draw midi-to-height)
(def midi-chan (midi/listener))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  {:cc0 0 :cc1 0})


(defn update-state [state ]
  {:cc0 (midi-to-255 (first (alts!! [midi/cc0] :default (:cc0 state))))
   :cc1 (midi-to-255 (first (alts!! [midi/cc1] :default (:cc1 state))))})

(defn draw-state [state]
  (q/background (:cc0 state) (:cc1 state) (:cc1 state)))

(q/defsketch quil-midi
  :features [:keep-on-top :exit-on-close]
  :size [800 800]
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
