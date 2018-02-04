(ns my-app.build.test-running
  (:require
   [my-app.build
    [check :as check]
    [dev :as dev]
    [time-reporting :as time-reporting]]
   ))

(defn all
  "Use this to check your work as you make changes"
  ([]
   (all false))
  ([do-not-refresh-all?]
   (let [started-at (time-reporting/get-time-in-ms-now)]
     (dev/safe-refresh do-not-refresh-all?)
     (check/lint-and-run-all-unit-tests)
     (check/run-smoke-tests)
     (time-reporting/measure-and-report-elapsed-time
      "Build and check finished after: "
      started-at)
     (println "CHECK OK!"))))

(defn without-linting
  ([]
   (without-linting false))
  ([do-not-refresh-all?]
   (let [started-at (time-reporting/get-time-in-ms-now)]
     (dev/safe-refresh do-not-refresh-all?)
     (check/run-all-unit-tests)
     (check/run-smoke-tests)
     (time-reporting/measure-and-report-elapsed-time
      "Test without linting finished after: "
      started-at))))

(defn without-linting-and-smoke-tests
  ([]
   (without-linting-and-smoke-tests false))
  ([do-not-refresh-all?]
   (let [started-at (time-reporting/get-time-in-ms-now)]
     (dev/safe-refresh do-not-refresh-all?)
     (check/run-all-unit-tests)
     (time-reporting/measure-and-report-elapsed-time
      "Test without linting and smoke tests finished after: "
      started-at))))
