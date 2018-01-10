(ns my-app.build.idiomatic
  (:require
   [boot.core :as boot]
   [clojure.java.io :as io]
   [kibit.driver :as kibit-driver]
   [kibit.rules.arithmetic :as kibit-arith]
   [kibit.rules.control-structures :as kibit-control]
   [kibit.rules.collections :as kibit-coll]
   ;; [kibit.rules.equality :as kibit-equality]
   [kibit.rules.misc :as kibit-misc]
   [my-app.backend.error :as error]
   ))

(defn- get-idiomatic-errors
  []
  (let [rules {:arithmetic kibit-arith/rules
               :collections kibit-coll/rules
               :control-structures kibit-control/rules
               #_(comment
                   "I don't like the equality rules as:"
                   (test/is (= false
                               (util/string-contains-substring?
                                "foobar"
                                "baz")))
                   "Gives much better test output than:"
                   (test/is (false?
                             (util/string-contains-substring?
                              "foobar"
                              "baz"))))
               ;; :equality kibit-equality/rules
               :misc kibit-misc/rules}]
    (kibit-driver/run
      (for [folder (boot/get-env :directories)
            :let [file-path (io/file (str folder "/my_app"))]
            :when (.exists file-path)
            ]
        file-path)
      (apply concat (vals rules)))))

(defn analyse []
  (println "Analysing the code for idiomatic errors...")
  (let [errors (get-idiomatic-errors)]
    (if (pos? (count errors))
      (error/throw-with-trace "Idiomatic errors!")
      :OK)))
