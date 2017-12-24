(ns my-app.frontend.core-test
  (:require
   [cljs.test :as test :include-macros true]))

(test/deftest core-test
  (test/testing "Number assertions"
    (test/is (= false (> 1 2)) "1 is smaller than 2")))
