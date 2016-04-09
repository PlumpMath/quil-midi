(ns quil-midi.midi (:require [overtone.midi :as midi])) 

(defn -main []
  (print (midi/midi-devices)))
