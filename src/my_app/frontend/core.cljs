(ns my-app.frontend.core
  (:require
   [accountant.core :as accountant]
   [bidi.bidi :as bidi]
   [my-app.frontend.all-tests]
   [my-app.frontend.components.pages :as pages]
   [my-app.frontend.routes :as routes]
   [reagent.core :as reagent]
   [reagent.session :as session]))

(defn main []
  [:div#rendered-app
   [:h1 "Hello, world!"]])

(defn page []
  (let [page (:current-page (session/get :route))]
    [:div
     [:p [:a {:href (bidi/path-for routes/app :index) } "Go home"]]
     [main]
     [:hr]
     ^{:key page} [pages/page-contents page]
     ]))

(defn render-component-in-element-with-id
  [component id]
  (reagent/render-component
   [component]
   (. js/document (getElementById id))))

(defn nav-handler
  [path-to-match]
  (let [match (bidi/match-route routes/app path-to-match)
        current-page (get match :handler)
        route-params (get match :route-params)]
    (session/put! :route {:current-page current-page
                          :route-params route-params})))

(defn path-exists-handler
  [path-to-match]
  (boolean (bidi/match-route routes/app path-to-match)))

(defn ^:export main-entry-point []
  (accountant/configure-navigation!
   {:nav-handler nav-handler
    :path-exists? path-exists-handler})
  (accountant/dispatch-current!)
  (render-component-in-element-with-id page "main-app"))
