(ns my-app.build.frontend
  (:require
   [cljs.build.api :as cljs-build]
   [clojure.java.shell :as clojure-java-shell]
   [clojure.string :as string]
   [my-app.build.time-reporting :as time-reporting]
   [my-app.build.util :as util]
   ))

(def generated-directory "generated/")
(def automated-testing-directory (str generated-directory "automated-testing/"))

(defn- clean-up
  []
  (util/delete-file-or-directory
   (str automated-testing-directory "karma_cljs"))
  (util/delete-file-or-directory (str automated-testing-directory "my_app")))

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
    (clean-up)
    (compile-cljs karma-directory karma-output-file)
    (compile-cljs app-directory app-output-file)
    (time-reporting/measure-and-report-elapsed-time
     "Compiled frontend code after: "
     started-at)))

(defn server-is-down?
  [exit-code output]
  (and
   (> exit-code 0)
   (string/includes? output "There is no server listening")))

(defn- throw-if-karma-server-is-down
  [exit-code output]
  (when (server-is-down? exit-code output)
    (throw
     (Exception.
      (str
       "Failed to run tests!\n"
       "Did you start the server? (./start_karma.sh)")))))

(defn- throw-if-tests-failed
  [exit-code]
  (when (> exit-code 0)
    (throw (Exception. "Frontend tests failed!"))))

(defn run-tests-with-karma
  []
  (println "Running frontend tests...")
  (let [started-at (time-reporting/get-time-in-ms-now)
        karma-binary-path "./node_modules/karma/bin/karma"
        process-results (clojure-java-shell/sh
                         karma-binary-path
                         "run"
                         "karma.config.js"
                         "--no-colors")
        output (get process-results :out)
        exit-code (get process-results :exit)]
    (println output)
    (throw-if-karma-server-is-down exit-code output)
    (throw-if-tests-failed exit-code)
    (time-reporting/measure-and-report-elapsed-time
     "Ran frontend tests after: "
     started-at)
    (println "Frontend Tests: OK")
    ))
