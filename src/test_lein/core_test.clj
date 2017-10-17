(ns test-lein.core-test
  (:require
   [clojure.test :as test]
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

(test/use-fixtures :each my-fixture)

(test/deftest says-hello
  (println "I'm running 1!")
  (test/is (= "Hello, world!" (test-lein/hello))))

(test/deftest nothing
  (println "I'm running 2!"))
