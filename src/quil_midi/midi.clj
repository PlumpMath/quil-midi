(ns quil-midi.midi (:require [overtone.midi :as midi]
                             [clojure.core.async :refer [go go-loop chan sliding-buffer >! >!! <! <!!]]))

(def sliding-chan (chan (sliding-buffer 1)))
(def iac-device-name "virtual-midi") 

(defn listener []
  "Returns a device that can be closed"
  (let [device (midi/midi-in iac-device-name)]
    (midi/midi-handle-events device (fn [x] (>!! sliding-chan x))
    (:device device))))
(def listener-device (listener))

(go-loop []
    (println (:data2 (<! sliding-chan)))
    (recur))

(defn log-msg [msg]
  (print (str "_ " msg  "\n")))

