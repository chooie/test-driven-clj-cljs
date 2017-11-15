(ns my-app.build.client
  (:require
   [me.raynes.fs :as fs]
   ))

(def build-directory "out/")

(defn clean
  []
  (fs/delete-dir build-directory))
