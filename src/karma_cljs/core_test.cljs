(ns karma-cljs.core-test
  (:require
   [cljs.test :as test :include-macros true]))

(test/deftest core-test
  (test/testing "Is true"
    (test/is false)))
