(ns karma-cljs.runner
  (:require
   [cljs.test :as test :include-macros true]))

(defn start-running-tests
  []
  (test/run-all-tests))
