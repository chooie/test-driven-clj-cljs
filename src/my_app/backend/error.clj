(ns my-app.backend.error
  (:require
   [clojure.stacktrace :as trace]))

(defn throw-with-trace
  [message]
  (trace/e)
  (throw (Exception. message)))
