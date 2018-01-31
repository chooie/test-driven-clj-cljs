(ns my-app.frontend.components.main
  (:require
   [bidi.bidi :as bidi]
   [my-app.frontend.components.pages :as pages]
   [my-app.frontend.routes :as routes]
   [reagent.session :as session]))

(defn main []
  [:div#rendered-app
   [:h1 "Hello, world!"]])

(defn page []
  (let [page-id (get (session/get :route) :current-page)]
    [:div
     [:p [:a
          {:href (bidi/path-for routes/app :index)}
          "Go home"]]
     [main]
     [:hr]
     ^{:key page-id} [pages/page-contents page-id]
     ]))
