(ns my-app.frontend.core-test
  (:require
   [cljs.test :as test :include-macros true]
   [my-app.common.css.core :as css]))

(test/deftest core-test
  (test/testing "Number assertions"
    (test/is (false? (> 1 2)) "1 is smaller than 2")))

(defn get-computed-style
  [element property]
  (let [styles (.getComputedStyle js/window element)
        property-value (.getPropertyValue styles property)]
    property-value))

(test/deftest styles-test
  (test/testing "Styles"
    (test/is (= css/light-green
                (get-computed-style (.-body js/document) "background-color")))))
