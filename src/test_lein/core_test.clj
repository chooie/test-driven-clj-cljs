(ns test-lein.core-test
  (:require
   [clojure.test :as test]
   [test-lein.core :as test-lein]))

(test/deftest says-hello
  (test/is (= "Hello, world!" (test-lein/hello))))
