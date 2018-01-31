(ns my-app.frontend.core
  (:require
   [cljs.reader :as cljs-reader]
   [my-app.common.log :as log]
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
  (let [config (cljs-reader/read-string js/_my_app_config)
        logging-level (get config :logging-level)]
    (log/set-logging-level! logging-level)
    (route-handler/setup)
    (render-component-in-element-with-id main/page "main-app")))
