(ns test-lein.config)

(defn get-config-for
  [profile]
  (condp = profile
    "development" {:port 8080
                   :logging-level :info}
    "test-automation" {:port 8081
                       :logging-level :warn}
    "production" {:port 80
                  :logging-level :warn}
    :default (throw (Exception. (str "No profile '" profile "'")))))
