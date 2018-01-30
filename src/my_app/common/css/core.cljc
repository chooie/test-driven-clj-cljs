(ns my-app.common.css.core
  (:require [garden.core :as garden]))

(def light-green "rgb(144, 238, 144)")

(defn generate-styles []
  (garden/css
   [:html
    {:background-color "lightblue"}]
   [:body
    {:background-color light-green}]))
