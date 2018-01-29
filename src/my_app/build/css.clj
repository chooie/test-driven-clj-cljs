(ns my-app.build.css
  (:require
   [my-app.css.core :as css]))

(defn build []
  (let [styles (css/generate-styles)]
    (println styles)))
