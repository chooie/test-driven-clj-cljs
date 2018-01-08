(ns my-app.frontend.core
  (:require
   [my-app.frontend.all-tests]
   [my-app.frontend.core-test]))

(.log js/console "Loaded frontend!")

(defn foo
  ^:export
  []
  (.log js/console "Ran foo!"))
