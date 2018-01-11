(ns my-app.build.external-dependencies
  (:require
   [clojure.java.shell :as clojure-java-shell]
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
  (System/getProperty "java.runtime.version"))

(defn check-java-version
  []
  (let [expected-java-version "1.8.0_144-b01"
        actual-java-version (get-java-version-from-current-runtime)]
    (when (not= expected-java-version actual-java-version)
      (throw (Exception.
              (str "Expected java-version: '"
                   expected-java-version
                   "', but actual was: '"
                   actual-java-version
                   "'."))))
    (println (str "Java Version Check: OK ("
                  actual-java-version
                  ")"))))
