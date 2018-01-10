(ns my-app.common.-util-test
  (:require
   [clojure.test :as test]
   [my-app.common.util :as util]))

(test/deftest string-checking
  (test/testing "returns true when substring is present in string"
    (test/is (= true (util/string-contains-substring? "foobar" "foo"))))
  (test/testing "returns false when substring is not present in string"
    (test/is (= false (util/string-contains-substring? "foobar" "baz")))))
