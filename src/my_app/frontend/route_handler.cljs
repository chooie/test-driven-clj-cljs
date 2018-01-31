(ns my-app.frontend.route-handler
  (:require
   [accountant.core :as accountant]
   [bidi.bidi :as bidi]
   [my-app.frontend.routes :as routes]
   [reagent.session :as session]))

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

(defn setup []
  (accountant/configure-navigation!
   {:nav-handler nav-handler
    :path-exists? path-exists-handler})
  (accountant/dispatch-current!))
