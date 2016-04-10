(ns quil-midi.midi
  (:import
   (java.sound.midi Sequencer Synthesizer
                    MidiSystem MidiDevice Reciever Transmitter MidiEvent
                    MidiMessage ShortMessage SysexMessage
                    InvalidMidiDataException MidiUnavailableException
                    MidiDevice$Info)))
