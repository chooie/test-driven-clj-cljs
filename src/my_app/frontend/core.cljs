(ns my-app.frontend.core
  (:require
   [my-app.frontend.all-tests]))

(.log js/console "Loaded frontend!")

(defn foo
  ^:export
  []
  (.log js/console "Ran foo!"))
