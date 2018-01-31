(ns my-app.build.lint
  (:require [eastwood.lint :as eastwood]
            [my-app.build.time-reporting :as time-reporting]
            [my-app.common.error :as error]))

(defonce lint-options
  [:bad-arglists
   :constant-test
   :def-in-def
   :deprecations
   :keyword-typos
   :local-shadows-var
   :misplaced-docstrings
   :no-ns-form-found
   ;; :non-clojure-file
   :redefd-vars
   :suspicious-expression
   :suspicious-test
   :unused-fn-args
   :unused-locals
   :unused-meta-on-macro
   :unused-namespaces
   :unused-private-vars
   :unused-ret-vals
   :wrong-arity
   :wrong-ns-form
   :wrong-pre-post
   :wrong-tag])

(defn get-info-warnings
  [warnings]
  (map (fn [warning]
         (select-keys warning [:msg :linter :file :column :line]))
       warnings))

(defn make-warning-message
  [warning-info]
  (str "Linting error '" (:linter warning-info) "' at "
       (:file warning-info) " line " (:line warning-info) ", column "
       (:column warning-info) ".\n"
       (:msg warning-info) ".\n"))

(defn- warnings?
  [lint-results]
  (pos? (count (:warnings lint-results))))

(defn- errors?
  [lint-results]
  (not= (get lint-results :errors) nil))

(defn lint-backend []
  (println "Linting the code...")
  (let [started-at (time-reporting/get-time-in-ms-now)
        lint-results (eastwood/lint {:source-paths ["src"]
                                     :add-linters lint-options})
        info-warnings (get-info-warnings (:warnings lint-results))
        warning-messages (map make-warning-message info-warnings)]
    (if (or (warnings? lint-results)
            (errors? lint-results))
      (do
        (run! println warning-messages)
        (error/throw "Lint error!"))
      (do
        (time-reporting/measure-and-report-elapsed-time
         "Linted the code after: "
         started-at)
        :OK))))
