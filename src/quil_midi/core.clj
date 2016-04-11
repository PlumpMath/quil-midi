(ns quil-midi.core
  (:require
   [clojure.core.match :refer [match]]
   [quil.core :as q]
   [quil.middleware :as m]
   [quil-midi.midi :as midi])
  (:import [processing.video Capture]))

(declare midi-to-255 log-msg update-atom)

(def state (atom {:val 0}))
(def camera (atom {:instance nil}))

;; DISPATCH
(defn dispatch-midi-event
  [midi-msg]
  (let [{n :note v :velocity :as msg} midi-msg]
    (match n
        0 (update-atom v))))


(defn update-atom [val]
    (if (not= val (:val @state))
      (swap! state assoc :val val)
      nil))

(midi/listener dispatch-midi-event)

(defn setup []
  (def cam (Capture. (quil.applet/current-applet) 400 400 (first (Capture/list))))
  (swap! camera assoc :instance cam)
  (.start (:instance @camera))
  (q/frame-rate 1))

(defn draw []
  (if (.available (:instance @camera))
    (.read (:instance @camera)))

  (q/image (:instance @camera) 0 0)
  )


(q/defsketch quil-midi
  :size [400, 400]
  :setup setup
  :features [:keep-on-top]
  :renderer :p3d
  :draw draw)

;; util

(defn midi-to-255 [x]
  (/ (* x 255) 127))

;; main

(defn -main [] (print "loaded"))
