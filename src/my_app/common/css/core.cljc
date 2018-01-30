(ns my-app.common.css.core
  (:require [garden.core :as garden]))

(defn generate-styles []
  (garden/css
   [:html
    {:background-color "lightblue"}]))
