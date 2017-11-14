(ns my-app.build.lint
  (:require
   [eastwood.lint :as eastwood]
   [my-app.backend.error :as error]
   ))

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

(defn lint []
  (let [lint-results (eastwood/lint {:source-paths ["src"]
                                     :add-linters lint-options})
        info-warnings (get-info-warnings (:warnings lint-results))
        warning-messages (map make-warning-message info-warnings)]
    (println "Linting the code...")
    (if (or (pos? (count (:warnings lint-results)))
            (not= (:errors lint-results) nil))
      (do
        (run! println warning-messages)
        (error/throw-with-trace "Lint error!"))
      :OK)))
