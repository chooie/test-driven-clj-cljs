(ns my-app.frontend.components.main
  (:require
   [my-app.frontend.components.pages :as pages]
   [my-app.frontend.routes :as routes]
   [my-app.frontend.routing :as routing]))

(defn main []
  [:div#rendered-app
   [:h1 "Hello, world!"]])

(defn page []
  (let [page-id (get (routing/get-route-state) :current-page)]
    [:div
     [:p [:a
          {:href (routing/get-url-for-route routes/app :index)}
          "Go home"]]
     [main]
     [:hr]
     ^{:key page-id} [pages/page-contents page-id]
     ]))
