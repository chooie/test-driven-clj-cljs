(ns my-app.backend.pages
  (:require
   [hiccup.page :as hiccup-page]))

(defn index []
  (hiccup-page/html5
   [:head
    [:title "My App"]]
   [:body
    [:h1#app-declaration "This is my app"]
    [:script {:src "goog/base.js"}]
    [:script {:src "goog/deps.js"}]
    [:script {:src "js/my_app.js"}]
    ]))
