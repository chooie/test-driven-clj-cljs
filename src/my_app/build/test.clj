(ns my-app.build.test
  (:require
   [eftest.report.pretty :as eftest-report]
   [eftest.runner :as eftest]
   [my-app.backend.error :as error]
   ))

(defn run-tests
  []
  (let [results (eftest/run-tests
                 (eftest/find-tests "src")
                 {:report eftest-report/report})
        number-of-fails (:fail results)]
    (if (pos? number-of-fails)
      (error/throw-with-trace (str "FAIL: " number-of-fails))
      :OK)))
