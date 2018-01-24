(ns my-app.build.check
  (:require
   [my-app.build
    [backend-tester :as backend-tester]
    [external-dependencies :as my-app-external-dependencies]
    [frontend :as frontend]
    [idiomatic :as idiomatic]
    [lint :as lint]
    [time-reporting :as time-reporting]]
   [my-app.smoke-test :as smoke]))

(defn analyse []
  (idiomatic/analyse))

(defn run-smoke-tests []
  (let [started-at (time-reporting/get-time-in-ms-now)]
    (println "Running smoke tests...")
    (smoke/run-tests)
    (time-reporting/measure-and-report-elapsed-time
     "Ran smoke tests after: "
     started-at)))

(defn full-backend-check []
  (frontend/build-cljs)
  (my-app-external-dependencies/check-java-version)
  (my-app-external-dependencies/check-node-process-version)
  (backend-tester/run-tests))

(defn lint-and-full-backend-check []
  (lint/lint-backend)
  (full-backend-check))

(defn frontend-check []
  (frontend/run-tests-with-karma))

(defn run-all-unit-tests []
  (full-backend-check)
  (frontend-check))

(defn lint-and-run-all-unit-tests []
  (lint/lint-backend)
  (run-all-unit-tests))
