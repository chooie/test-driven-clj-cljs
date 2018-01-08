(ns my-app.build.backend-tester
  (:require
   [eftest.report.pretty :as eftest-report]
   [eftest.runner :as eftest]
   [my-app.backend.error :as error]
   ))

(defn run-tests
  []
  (println "Running backend tests...")
  (let [results (eftest/run-tests
                 (eftest/find-tests "src")
                 {:report eftest-report/report})
        number-of-fails (get results :fail)
        number-of-errors (get results :error)]
    (if (or (pos? number-of-fails)
            (pos? number-of-errors))
      (error/throw-with-trace
       (str "FAIL:\n"
            "Failures: " number-of-fails "\n"
            "Errors: " number-of-errors))
      (do
        (println "Backend Tests: OK")
        :OK))))
