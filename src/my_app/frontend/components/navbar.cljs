(ns my-app.frontend.components.navbar
  (:require
   [bidi.bidi :as bidi]
   [my-app.frontend.routes :as routes]))

(defn component []
  [:ul
   [:li
    [:a {:href (bidi/path-for routes/app :section-a)}
     "Section A"]]
   [:li
    [:a {:href (bidi/path-for routes/app :section-b)}
     "Section B"]]
   [:li
    [:a {:href (bidi/path-for routes/app :missing-route)}
     "Missing-route"]]
   [:li
    [:a {:href "/broken/link"}
     "Broken link"]]])
