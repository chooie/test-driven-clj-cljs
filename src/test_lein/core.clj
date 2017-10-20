(ns test-lein.core
  (:require
   [com.stuartsierra.component :as component]
   [test-lein.config :as config]
   [test-lein.log :as log]
   [test-lein.server :as server])
  (:gen-class))

(defn system [profile]
  (let [config (config/get-config-for profile)]
    (log/set-logging-level! (:logging-level config))
    (log/info "Building system...")
    (component/system-map
     :server (server/new-server (:port config)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (component/start (system "production")))
