(ns quil-midi.midi
  (:require [overtone.midi :as midi]
            [clojure.core.match :refer [match]]
            [clojure.core.async :refer [go go-loop chan sliding-buffer >! >!! <! <!!]]))

(def cc0 (chan (sliding-buffer 1)))
(def cc1 (chan (sliding-buffer 1)))

(def iac-device-name "virtual-midi")

(defn dispatch
  [midi-msg]
  "Dispatches a midi message to the appropriate channel"
  (let [{n :note
         v :velocity} midi-msg]
    (match n
           0 (>!! cc0 v)
           1 (>!! cc1 v))))

(defn listener []
  "Returns a device that can be closed"
  (let [device (midi/midi-in iac-device-name)]
    (midi/midi-handle-events device dispatch)
    (:device device)))

