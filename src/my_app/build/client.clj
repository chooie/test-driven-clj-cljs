(ns my-app.build.client
  (:require
   [me.raynes.fs :as fs]
   ))

(def ^:const build-directory "out/")

(defn clean
  []
  (println (str "Clearing the contents of the '" build-directory "' directory"))
  (fs/delete-dir build-directory))
