(ns idiomatic
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [kibit.driver :as kibit-driver]))

(defn- get-idiomatic-errors
  []
  (kibit-driver/run
    (for [folder ["src" "dev"]
          :let [file-path (io/file folder)]
          :when (.exists file-path)
          ]
      file-path)
    nil))

(defn analyse []
  (let [errors (get-idiomatic-errors)]
    (if (pos? (count errors))
      (throw (Exception. "Idiomatic errors!"))
      :OK)))
