(ns my-app.build.client
  (:require
   [me.raynes.fs :as fs]
   ))

(def ^:const build-directory "out/")

(defn clean
  []
  (fs/delete-dir build-directory))
