(ns test-lein.core-test
  (:require [clojure.test :refer :all]
            [test-lein.core :as test-lein]))

(deftest core-test
  (testing "Says hello world"
    (is (= "Hello, world!" (test-lein/hello)))))
