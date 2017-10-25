(ns my-app.boot.test
  (:require
   [clojure.test :as test]
   [eftest.report.pretty :as eftest-report]
   [eftest.runner :as eftest]
   ))

(defn run-tests
  []
  (let [results (eftest/run-tests
                 (eftest/find-tests "src")
                 {:report eftest-report/report})
        number-of-fails (:fail results)]
    (if (pos? number-of-fails)
      (throw (Exception. (str "FAIL: " number-of-fails)))
      :OK)))
