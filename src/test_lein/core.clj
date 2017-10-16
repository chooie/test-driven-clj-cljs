(ns test-lein.core
  (:require [com.stuartsierra.component :as component])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn hello
  []
  "Hello, world!")

(defrecord Server [port]
  component/Lifecycle

  (start [component]
    (println "Starting Server...")
    (let [])))
