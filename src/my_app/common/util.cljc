(ns my-app.common.util
  (:require
   [clojure.string :as string]))

(defn string-contains-substring?
  [string-to-check substring]
  (string/includes? string-to-check substring))
