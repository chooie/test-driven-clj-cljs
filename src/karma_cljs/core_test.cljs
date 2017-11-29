(ns karma-cljs.core-test
  (:require
   [cljs.test :as test :include-macros true]))

(enable-console-print!)
(println "I've got the test namespace!")

(test/deftest core-test
  (test/testing "Is true"
    (test/is false)))
