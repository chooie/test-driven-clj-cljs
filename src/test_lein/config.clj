(ns test-lein.config)

(defn get-config-for
  [profile]
  (condp = profile
    "development" {:port 8080}
    "test-automation" {:port 8081}
    :default (throw (Exception. (str "No profile '" profile "'")))))
