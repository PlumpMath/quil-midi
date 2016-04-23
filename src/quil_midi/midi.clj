(ns quil-midi.midi
    (:require [overtone.midi :as midi]
              [clojure.core.async :refer [sliding-buffer chan go <! <!! >! >!!]]))


(def iac-device-name "virtual-midi")
(def devices (midi/midi-devices))

(defn iac?
  [device]
  (= (get device :name) iac-device-name))

(defn check-iac[]
  (print (if (seq (filter iac? devices))
           "IAC device located"
           (str "Cannot locate IAC device with name " iac-device-name))))

; Delete this
(defn get-input-device []
  (:device (midi/midi-in iac-device-name)))

(defn close
  [device]
  (.close device))

(defn listener []
  "Returns a channel"
  (let [device (midi/midi-in iac-device-name)
        channel (chan (sliding-buffer 10))]
    (midi/midi-handle-events device (fn [msg] (go (>! chan msg))))
    channel))

(defn log-msg [msg]
  (print (str "_ " msg  "\n")))

