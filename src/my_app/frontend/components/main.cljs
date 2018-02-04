(ns my-app.frontend.components.main
  (:require
   [my-app.frontend.components.common :as common]
   [my-app.frontend.components.pages :as pages]
   [my-app.frontend.routes :as routes]
   [my-app.frontend.routing :as routing]))

(defn main []
  [:div#rendered-app
   [:h1 "Hello, world!"]])

(defn page []
  (let [page-route (routing/get-current-page-route)]
    [:div
     [common/link (routing/get-url-for-route routes/app :index) "Go home"]
     [main]
     [:hr]
     ^{:key page-route} [pages/page-contents page-route]
     ]))
