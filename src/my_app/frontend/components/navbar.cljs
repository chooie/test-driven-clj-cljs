(ns my-app.frontend.components.navbar
  (:require
   [bidi.bidi :as bidi]
   [my-app.frontend.components.common :as common]
   [my-app.frontend.routing :as routing]
   [my-app.frontend.routes :as routes]))

(defn component []
  [:ul
   [:li
    [common/link
     (routing/get-url-for-route routes/app :section-a)
     "Section A"]]
   [:li
    [common/link
     (routing/get-url-for-route routes/app :section-b)
     "Section B"]]
   [:li
    [common/link (bidi/path-for routes/app :missing-route)
     "Missing-route"]]
   [:li
    [common/link "/broken/link" "Broken link"]]])
