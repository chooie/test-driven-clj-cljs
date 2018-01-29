(ns my-app.frontend.core
  (:require
   [my-app.frontend.all-tests]
   [reagent.core :as reagent]))

(defn main []
  [:h1 "Hello, world!"])

(defn main-entry-point ^:export
  []
  (reagent/render [main] (js/document.getElementById "main-app")))
