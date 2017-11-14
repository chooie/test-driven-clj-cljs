(ns my-app.build.idiomatic
  (:require
   [boot.core :as boot]
   [clojure.java.io :as io]
   [kibit.driver :as kibit-driver]
   [my-app.backend.error :as error]
   ))

(defn- get-idiomatic-errors
  []
  (kibit-driver/run
    (for [folder (boot/get-env :directories)
          :let [file-path (io/file folder)]
          :when (.exists file-path)
          ]
      file-path)
    nil))

(defn analyse []
  (println "Analysing the code for idiomatic errors...")
  (let [errors (get-idiomatic-errors)]
    (if (pos? (count errors))
      (error/throw-with-trace "Idiomatic errors!")
      :OK)))
