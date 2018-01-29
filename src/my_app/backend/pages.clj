(ns my-app.backend.pages
  (:require
   [hiccup.page :as hiccup-page]))

(defn get-body-for-profile
  "
  We need different bodies for dev and test due to the differences in how the
  js is output
  "
  [profile]
  (if (= profile :test-automation)
    [:body
     "<!--This is my test app smoke marker-->"
     [:div#main-app]
     [:script {:src "goog/base.js"}]
     [:script {:src "goog/deps.js"}]
     [:script {:src "js/my_app.js"}]
     [:script
      "goog.require('my_app.frontend.core');"]
     [:script
      "my_app.frontend.core.main_entry_point();"]]
    [:body
     "<!--This is my dev app smoke marker-->"
     [:div#main-app]
     [:script {:src "main.js"}]
     ]))

(defn index
  [profile]
  (hiccup-page/html5
   [:head
    [:title "My App"]
    [:link {:rel "shortcut icon"
            :type "image/png"
            :href "/clojure_logo.svg.png"}]]
   (get-body-for-profile profile)))

(defn not-found []
  (hiccup-page/html5
   [:head
    [:title "404 - Not Found"]]
   [:body
    [:h1 "404"]
    [:p "Sorry, we couldn't find the page you're looking for :("]]))
