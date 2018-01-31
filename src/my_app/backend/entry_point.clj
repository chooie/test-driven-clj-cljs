(ns my-app.backend.entry-point
  (:require
   [com.stuartsierra.component :as component])
  ;; This needs to be aot so that we can run the jar
  (:gen-class))

(defn init
  []
  (require 'my-app.backend.core)
  ((resolve 'my-app.backend.core/system) :production))

(defn -main
  [& _args]
  (component/start (init)))
