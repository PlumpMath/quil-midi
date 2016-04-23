(ns quil-midi.async
  (:require
[clojure.core.async :refer [sliding-buffer chan go <! <!! >! >!!]]))

(def midi-chan (chan (sliding-buffer 10)))

(go (>! midi-chan "value"))
