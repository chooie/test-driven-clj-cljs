(ns my-app.build.frontend
  (:require
   [cljs.build.api :as cljs-build]
   [clojure.java.shell :as clojure-java-shell]
   [clojure.string :as string]
   [my-app.build.config :as config]
   [my-app.build.time-reporting :as time-reporting]
   [my-app.build.util :as util]
   ))

(defn- clean-up
  []
  (util/delete-file-or-directory
   (str config/automated-testing-directory "karma_cljs"))
  (util/delete-file-or-directory
   (str config/automated-testing-directory "my_app")))

(defn- compile-cljs
  [karma-directory karma-output-file]
  (cljs-build/build
   karma-directory
   {:output-dir config/automated-testing-directory
    :output-to (str config/automated-testing-directory karma-output-file)
    :parallel-build true
    :optimizations :none
    ;; :compiler-stats :true
    }))

(defn build-cljs
  []
  (println "Generating frontend code...")
  (let [started-at (time-reporting/get-time-in-ms-now)
        karma-directory "src/karma_cljs"
        karma-output-file "js/karma_cljs.js"
        app-directory "src/my_app/frontend/"
        app-output-file "js/my_app.js"]
    ;; Probably don't need this
    #_(clean-up)
    (compile-cljs karma-directory karma-output-file)
    (compile-cljs app-directory app-output-file)
    (time-reporting/measure-and-report-elapsed-time
     "Compiled frontend code after: "
     started-at)))

(defn server-is-down?
  [exit-code output]
  (and
   (pos? exit-code)
   (string/includes? output "There is no server listening")))

(defn- throw-if-karma-server-is-down
  [exit-code output]
  (when (server-is-down? exit-code output)
    (throw
     (Exception.
      (str
       "Failed to run tests!\n"
       "Did you start the server? (./bash_scripts/start_karma.sh)")))))

(defn- throw-if-tests-failed
  [exit-code]
  (when (pos? exit-code)
    (throw (Exception. "Frontend tests failed!"))))

(defonce frontend-test-results (atom false))

(defn frontend-tests-failed-last? []
  @frontend-test-results)

(defn run-tests-with-karma
  []
  (println "Running frontend tests...")
  (try
    (let [started-at (time-reporting/get-time-in-ms-now)
          karma-binary-path "./node_modules/karma/bin/karma"
          process-results (clojure-java-shell/sh
                           karma-binary-path
                           "run"
                           "tool_configurations/karma.config.js"
                           "--no-colors")
          output (get process-results :out)
          exit-code (get process-results :exit)]
      (println output)
      (throw-if-karma-server-is-down exit-code output)
      (throw-if-tests-failed exit-code)
      (time-reporting/measure-and-report-elapsed-time
       "Ran frontend tests after: "
       started-at)
      (reset! frontend-test-results false)
      (println "Frontend Tests: OK"))
    (catch Exception e
      (reset! frontend-test-results true)
      (throw e))))

(defn- run-in-shell
  [bash-command]
  (clojure-java-shell/sh "bash" "-c" bash-command))

(defonce last-frontend-hash-results (atom nil))

(defn frontend-contents-changed? []
  (let [process-results (run-in-shell
                         "tar c src/my_app/frontend src/my_app/common | md5")
        md5-hash (string/trim (get process-results :out))]
    (if (not= @last-frontend-hash-results md5-hash)
      (do
        (reset! last-frontend-hash-results md5-hash)
        true)
      false)))
