(ns my-app.backend.config
  (:require [my-app.common.error :as error]))

(defn get-port-environment-variable
  []
  (when-let [port-string (System/getenv "PORT")]
    (Integer. port-string)))

(defn get-config-for
  [profile]
  (case profile
    :development {:protocol "http"
                  :host "localhost"
                  :port 8080
                  :logging-level :info}
    :test-automation {:protocol "http"
                      :host "localhost"
                      :port 8081
                      :logging-level :warn}
    :production {:protocol "http"
                 :host "localhost"
                 :port (or (get-port-environment-variable) 80)
                 :logging-level :info}
    (error/throw (str "No profile '" profile "'"))))

(defn get-fully-qualified-url
  [config]
  (str (:protocol config) "://" (:host config) ":" (:port config) "/"))
