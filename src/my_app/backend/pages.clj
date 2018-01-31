(ns my-app.backend.pages
  (:require
   [hiccup.page :as hiccup-page]
   [my-app.backend.config :as config]))

(def favicon-link
  [:link {:rel "shortcut icon"
          :type "image/png"
          :href "/clojure_logo.svg.png"}])

(def reset-css-link
  [:link {:rel "stylesheet"
          :href "/reset.css"}])

(def css-link
  [:link {:rel "stylesheet"
          :href "/css/main.css"}])

(defn get-body-for-profile
  "
  We need different bodies for dev and test due to the differences in how the
  js is output
  "
  [profile]
  (if (= profile :test-automation)
    [:div
     "<!--This is my test app smoke marker-->"
     [:div#main-app]
     [:script {:src "goog/base.js"}]
     [:script {:src "goog/deps.js"}]
     [:script {:src "/js/my_app.js"}]
     [:script
      "goog.require('my_app.frontend.core');"]
     [:script
      "my_app.frontend.core.main_entry_point();"]]
    [:div
     "<!--This is my dev app smoke marker-->"
     [:div#main-app]
     [:script {:src "main.js"}]
     ]))

(defn index
  [profile]
  (hiccup-page/html5
   [:head
    [:base {:href "/"}]
    [:title "My App"]
    favicon-link
    reset-css-link
    css-link
    [:script
     (str "var _my_app_config = '" (config/get-config-for profile)) "';"]]
   [:body
    (get-body-for-profile profile)
    ]))
