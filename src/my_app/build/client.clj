(ns my-app.build.client
  (:require
   [cljs.build.api :as cljs-build]
   [me.raynes.fs :as fs]
   ))

(def build-directory "out/")

(defn clean
  []
  (fs/delete-dir build-directory))

(defn build-cljs
  []
  (clean)
  (fs/copy-dir "resources/public" build-directory)
  (cljs-build/build "src" {:output-to (str build-directory "js/main.js")}))
