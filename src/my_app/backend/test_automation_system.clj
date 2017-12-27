(ns my-app.backend.test-automation-system
  (:require
   [com.stuartsierra.component :as component]
   [my-app.backend.core :as my-app]
   ))

(def system nil)

(defn init
  []
  (alter-var-root
   #'system
   (constantly (my-app/system :test-automation))))

(defn start []
  (alter-var-root #'system component/start))

(defn stop []
  (alter-var-root
   #'system
   (fn [system] (when system (component/stop system)))))
