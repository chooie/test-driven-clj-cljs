(ns my-app.backend.core
  (:require
   [com.stuartsierra.component :as component]
   [my-app.backend.config :as config]
   [my-app.backend.log :as log]
   [my-app.backend.server :as server]))

(defn system [profile]
  (let [config (config/get-config-for profile)
        logging-level (:logging-level config)]
    (log/set-logging-level! logging-level)
    (when (= profile :development)
      (println (str "Logging level set to '" logging-level "'")))
    (log/info "Building system...")
    (component/system-map
     :server (server/new-server (:port config) profile))))
