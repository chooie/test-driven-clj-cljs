(ns my-app.build.client
  (:require
   [cljs.build.api :as cljs-build]
   [me.raynes.fs :as fs]
   ))

(def ^:const generated-directory "out/")

(defn clean
  []
  (fs/delete-dir generated-directory))

(defn emphasize-string
  [string]
  (str "'" string "'"))

(defn build-cljs
  []
  (let [output-to (str generated-directory "js/main.js")
        public-resources "resources/public"]
    (clean)
    (fs/copy-dir public-resources generated-directory)
    (cljs-build/build "src" {:output-to output-to})
    (println (str "Resources at " (emphasize-string public-resources)
                  " copied to " (emphasize-string generated-directory)))
    (println (str "Clojurescript source files built to "
                  (emphasize-string output-to)))))
