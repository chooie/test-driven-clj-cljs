(ns my-app.boot.client
  (:require
   [cljs.build.api :as cljs-build]
   [me.raynes.fs :as fs]
   ))

(defn clean
  []
  (fs/delete-dir "out"))

(defn build-cljs
  []
  (clean)
  (fs/copy-dir "resources/public" "out")
  (cljs-build/build "src" {:output-to "out/js/main.js"}))
