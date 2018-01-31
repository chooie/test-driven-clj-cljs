(ns my-app.common.log
  (:require [my-app.common.error :as error]
            [taoensso.timbre :as timbre]))

(def log-levels #{:trace :debug :info :warn :error :fatal :report})

(defn is-valid-log-level?
  [possible-log-levels-set log-level]
  (contains? possible-log-levels-set log-level))

(defn set-logging-level!
  [level-key]
  (if (not (is-valid-log-level? log-levels level-key))
    (error/throw
     (str
      "'" level-key "' is not a valid log level. Try one " "of: " log-levels))
    (timbre/merge-config! {:level level-key})))

(defn info
  [message]
  (timbre/info message))
