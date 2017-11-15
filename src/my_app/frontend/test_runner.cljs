(ns my-app.frontend.test-runner
  (:require
   [jx.reporter.karma :as karma-reporter :include-macros true]
   ))

(defn ^:export run-all-tests
  [karma]
  (karma-reporter/run-all-tests karma))
