(ns my-app.frontend.core
  (:require
   [my-app.frontend.all-tests]
   [my-app.frontend.components.main :as main]
   [my-app.frontend.route-handler :as route-handler]
   [reagent.core :as reagent]))

(defn render-component-in-element-with-id
  [component id]
  (reagent/render-component
   [component]
   (. js/document (getElementById id))))

(defn ^:export main-entry-point []
  (route-handler/setup)
  (render-component-in-element-with-id main/page "main-app"))
