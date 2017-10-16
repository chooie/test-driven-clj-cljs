(ns test-lein.core-test
  (:require [clojure.test :refer :all]
            [test-lein.core :as test-lein]))

(defn- before
  []
  #_(println "ran first!"))

(defn- after
  []
  #_(println "ran last!"))

(defn my-fixture
  [each-test]
  (before)
  (each-test)
  (after))

(use-fixtures :each my-fixture)

(deftest says-hello
  (println "I'm running 1!")
  (is (= "Hello, world!" (test-lein/hello))))

(deftest nothing
  (println "I'm running 2!"))
