(ns my-app.build.check
  (:require
   [my-app.build.backend-tester :as backend-tester]
   [my-app.build.external-dependencies :as my-app-external-dependencies]
   [my-app.build.frontend :as frontend]
   [my-app.build.idiomatic :as idiomatic]
   [my-app.build.lint :as lint]
   [my-app.smoke-test :as smoke]
   [my-app.build.time-reporting :as time-reporting]
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

(defn check []
  (backend-checks)
  (my-app-external-dependencies/check-node-process-version)
  (frontend/run-tests-with-karma)
  (run-smoke-tests)
  (println "CHECK OK!"))
