(ns my-app.build.frontend
  (:require
   [cljs.build.api :as cljs-build]
   [clojure.java.io :as io]
   [clojure.java.shell :as clojure-java-shell]
   ))

(def generated-directory "generated/")
(def automated-testing-directory (str generated-directory "automated-testing/"))

(declare delete-recursively)
(defn- delete-all-files-in-directory
  [file]
  (doseq [current-file (.listFiles file)]
    (delete-recursively current-file)))

(defn- delete-recursively
  [file]
  (when (.isDirectory file)
    (delete-all-files-in-directory file))
  (io/delete-file file))

(defn delete-file-or-directory
  [path]
  (let [file (io/file path)]
    (when (.exists file)
      (delete-recursively file))))

(defn- compile-cljs
  [karma-directory karma-output-file]
  (cljs-build/build
   karma-directory
   {:output-dir automated-testing-directory
    :output-to (str automated-testing-directory karma-output-file)
    :parallel-build true}))

(defn- get-time-in-ms-now
  []
  (System/currentTimeMillis))

(defn- get-time-in-seconds
  [elapsed]
  (with-precision 2
    (/ (double elapsed) 1000)))

(defn- get-elapsed-time-in-seconds
  [starting-time ending-time]
  (get-time-in-seconds (- ending-time starting-time)))

(defn- measure-and-report-elapsed-time
  [started-at]
  (let [finished-at (get-time-in-ms-now)
        elapsed-seconds (get-elapsed-time-in-seconds
                         started-at
                         finished-at)
        expression-string (str
                           "Compiled frontend code after: "
                           elapsed-seconds
                           " seconds")]
    (println expression-string)))

(defn build-cljs
  []
  (println "Generating frontend code...")
  (let [started-at (get-time-in-ms-now)
        karma-directory "src/karma_cljs"
        karma-output-file "js/karma_cljs.js"
        app-directory "src/my_app/frontend/"
        app-output-directory "js/my_app.js"]
    (delete-file-or-directory automated-testing-directory)
    (compile-cljs karma-directory karma-output-file)
    (compile-cljs app-directory app-output-directory)
    (measure-and-report-elapsed-time started-at)))

(defn run-tests-with-karma
  []
  (println "Running frontend tests...")
  (let [karma-binary-path "./node_modules/karma/bin/karma"
        process-results (clojure-java-shell/sh
                         karma-binary-path
                         "run"
                         "karma.config.js"
                         "--no-colors")
        output (get process-results :out)
        exit-code (get process-results :exit)]
    (println output)
    (when (> exit-code 0)
      (throw (Exception. "Frontend tests failed!")))
    (println "Frontend Tests: OK")))
