(ns my-app.build.util
  (:require
   [clojure.java.io :as io]
   ))

(declare delete-recursively)
(defn- delete-all-files-in-directory
  [file]
  (doseq [current-file (.listFiles file)]
    (delete-recursively current-file)))

(defn- delete-recursively
  [file]
  (when (.isDirectory file)
    (delete-all-files-in-directory file))
  (io/delete-file file))

(defn delete-file-or-directory
  [path]
  (let [file (io/file path)]
    (when (.exists file)
      (delete-recursively file))))
