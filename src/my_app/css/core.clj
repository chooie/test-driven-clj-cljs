(ns my-app.css.core
  (:require [garden.core :as garden]))

(defn generate-styles []
  (garden/css
   [:body
    {:backround-color "blue"}]))
