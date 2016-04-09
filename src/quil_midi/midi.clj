(ns quil-midi.midi (:require [overtone.midi :as midi])) 

(def iac-device-name "virtual-midi")
(def devices (midi/midi-devices))

(def iac?
  (fn [device]
    (= (get device :name) iac-device-name)))

(defn -main []
  (print (if (seq (filter iac? devices))
           "IAC device located"
           (str "Cannot locate IAC device with name " iac-device-name))))
