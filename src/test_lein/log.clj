(ns test-lein.log
  (:require
   [taoensso.timbre :as timbre]))

(defn set-logging-level!
  [level-key]
  (timbre/merge-config! {:level level-key}))

(defn info
  [message]
  (timbre/info message))

;; Default logging level
(set-logging-level! :warn)
