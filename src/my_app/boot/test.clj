(ns my-app.boot.test
  (:require
   [eftest.runner :as eftest]
   ))

(defn run-tests
  []
  (let [results (eftest/run-tests (eftest/find-tests "src"))
        number-of-fails (:fail results)]
    (if (pos? number-of-fails)
      (throw (Exception. (str "FAIL: " number-of-fails)))
      :OK)))
