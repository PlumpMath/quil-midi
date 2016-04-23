(ns quil-midi.midi (:require [overtone.midi :as midi]
                             [clojure.core.async :refer [go go-loop chan sliding-buffer >! >!! <! <!!]]))


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
  "Returns a device that can be closed"
  (let [device (midi/midi-in iac-device-name)]
    (midi/midi-handle-events device (fn [x] (>!! sliding-chan x))
    (:device device))))

(def sliding-chan (chan (sliding-buffer 1)))
(def iac-device-name "virtual-midi") 

(def listener-device (listener))

(go-loop []
    (println (:data2 (<! sliding-chan)))
    (recur))

(defn log-msg [msg]
  (print (str "_ " msg  "\n")))

