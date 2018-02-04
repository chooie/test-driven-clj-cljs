(ns my-app.frontend.components.navbar
  (:require
   [bidi.bidi :as bidi]
   [my-app.frontend.routing :as routing]
   [my-app.frontend.routes :as routes]))

(defn component []
  [:ul
   [:li
    [:a {:href (routing/get-url-for-route routes/app :section-a)}
     "Section A"]]
   [:li
    [:a {:href (routing/get-url-for-route routes/app :section-b)}
     "Section B"]]
   [:li
    [:a {:href (bidi/path-for routes/app :missing-route)}
     "Missing-route"]]
   [:li
    [:a {:href "/broken/link"}
     "Broken link"]]])
