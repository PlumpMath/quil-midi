(defproject quil-midi "0.1.0-SNAPSHOT"
  :description "Quil with Ableton MIDI Interop"
  :url ""
  :main quil-midi.midi
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [org.clojure/core.async "0.2.374"]
                 [overtone/midi-clj "0.5.0"]
                 [quil "2.4.0"]]

  :resource-paths ["library/gstreamer-java.jar"
                   "library/jna.jar"
                   "library/video.jar"
                   "library/macosx64"]) 
