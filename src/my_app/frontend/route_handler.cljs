(ns my-app.frontend.route-handler
  (:require
   [accountant.core :as accountant]
   [bidi.bidi :as bidi]
   [reagent.session :as session]))

(defn set-route-state
  [current-page route-params]
  (session/put!
   :route {:current-page current-page
           :route-params route-params}))

(defn get-route-state []
  (session/get :route))

(defn nav-handler
  [routes path-to-match]
  (let [match (bidi/match-route routes path-to-match)
        current-page (get match :handler)
        route-params (get match :route-params)]
    (set-route-state current-page route-params)))

(defn path-exists-handler
  [routes path-to-match]
  (boolean (bidi/match-route routes path-to-match)))

(defn setup-navigation-with-routes
  [routes]
  (accountant/configure-navigation!
   {:nav-handler (partial nav-handler routes)
    :path-exists? (partial path-exists-handler routes)}))

(defn navigate-to-route
  [route]
  (accountant/navigate! route))

(defn dispatch-to-current-window-route []
  (accountant/dispatch-current!))

(defn setup [routes]
  (setup-navigation-with-routes routes)
  (dispatch-to-current-window-route))
