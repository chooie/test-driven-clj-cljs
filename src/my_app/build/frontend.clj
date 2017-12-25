(ns my-app.build.frontend
  (:require
   [cljs.build.api :as cljs-build]
   [clojure.java.shell :as clojure-java-shell]
   [my-app.build.time-reporting :as time-reporting]
   [my-app.build.util :as util]
   ))

(def generated-directory "generated/")
(def automated-testing-directory (str generated-directory "automated-testing/"))

(defn- compile-cljs
  [karma-directory karma-output-file]
  (cljs-build/build
   karma-directory
   {:output-dir automated-testing-directory
    :output-to (str automated-testing-directory karma-output-file)
    :parallel-build true}))

(defn build-cljs
  []
  (println "Generating frontend code...")
  (let [started-at (time-reporting/get-time-in-ms-now)
        karma-directory "src/karma_cljs"
        karma-output-file "js/karma_cljs.js"
        app-directory "src/my_app/frontend/"
        app-output-file "js/my_app.js"]
    (util/delete-file-or-directory automated-testing-directory)
    (compile-cljs karma-directory karma-output-file)
    (compile-cljs app-directory app-output-file)
    (time-reporting/measure-and-report-elapsed-time
     "Compiled frontend code after: "
     started-at)))

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
