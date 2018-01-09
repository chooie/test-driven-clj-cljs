(ns my-app.backend.pages
  (:require
   [hiccup.page :as hiccup-page]))

(defn get-body-for-profile
  [profile]
  (if (= profile :test-automation)
    [:body
     [:h1#app-declaration "This is my app"]
     [:script {:src "goog/base.js"}]
     [:script {:src "goog/deps.js"}]
     [:script {:src "js/my_app.js"}]
     [:script "goog.require('my_app.frontend.core')"]
     ]
    [:body
     [:h1#app-declaration "This is my dev app"]
     [:script {:src "main.js"}]
     ]))

(defn index
  [profile]
  (hiccup-page/html5
   [:head
    [:title "My App"]]
   (get-body-for-profile profile)))

(defn not-found []
  (hiccup-page/html5
   [:head
    [:title "404 - Not Found"]]
   [:body
    [:h1 "404"]
    [:p "Sorry, we couldn't find the page you're looking for :("]]))
