(ns my-app.backend.config
  (:require
   [my-app.backend.error :as error]))

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
                 :port 80
                 :logging-level :info}
    (error/throw-with-trace (str "No profile '" profile "'"))))

(defn get-fully-qualified-url
  [config]
  (str (:protocol config) "://" (:host config) ":" (:port config) "/"))
