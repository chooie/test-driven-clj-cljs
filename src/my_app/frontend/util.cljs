(ns my-app.frontend.util
  (:require
   [reagent.core :as reagent]))

(defn render-component-in-element-with-id
  [component id]
  (reagent/render-component
   [component]
   (. js/document (getElementById id))))
