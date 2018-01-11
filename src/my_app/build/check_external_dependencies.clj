(ns my-app.build.check-external-dependencies
  (:require
   [clojure.java.shell :as clojure-java-shell]
   [my-app.backend.error :as error]
   ))

(defn check-node-process-version
  []
  (let [process-results (clojure-java-shell/sh
                         "node"
                         "src/javascript/check_node_version.js")
        exit-code (get process-results :exit)
        output (get process-results :out)
        error (get process-results :err)]
    (when (pos? exit-code)
      (throw (Exception. (if (not (empty? output))
                           output
                           error))))
    (println output)))

(defn get-java-version-from-current-runtime
  []
  (System/getProperty "java.version"))
