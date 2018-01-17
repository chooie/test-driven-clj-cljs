(ns my-app.build.check
  (:require
   [my-app.build
    [backend-tester :as backend-tester]
    [external-dependencies :as my-app-external-dependencies]
    [frontend :as frontend]
    [idiomatic :as idiomatic]
    [lint :as lint]
    [time-reporting :as time-reporting]]
   [my-app.smoke-test :as smoke]
   ))

(defn analyse []
  (idiomatic/analyse))

(defn run-smoke-tests []
  (let [started-at (time-reporting/get-time-in-ms-now)]
    (println "Running smoke tests...")
    (smoke/run-tests)
    (time-reporting/measure-and-report-elapsed-time
     "Ran smoke tests after: "
     started-at)))

(defn backend-checks
  []
  (frontend/build-cljs)
  (my-app-external-dependencies/check-java-version)
  (lint/lint-backend)
  (backend-tester/run-tests))

(defn check
  ([]
   (check false))
  ([do-not-run-smoke-tests?]
   (backend-checks)
   (my-app-external-dependencies/check-node-process-version)
   (frontend/run-tests-with-karma)
   (when (false? do-not-run-smoke-tests?)
     (run-smoke-tests))
   (println "CHECK OK!")))
