(ns my-app.backend.core
  (:require
   [com.stuartsierra.component :as component]
   [my-app.backend.config :as config]
   [my-app.backend.log :as log]
   [my-app.backend.server :as server])
  (:gen-class))

(defn system [profile]
  (let [config (config/get-config-for profile)]
    (log/set-logging-level! (:logging-level config))
    (log/info "Building system...")
    (component/system-map
     :server (server/new-server (:port config)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& _args]
  (component/start (system "production")))
