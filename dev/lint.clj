(ns lint
  (:require
   [clojure.pprint :refer [pprint]]
   [eastwood.lint :as eastwood]))

(defn lint []
  (let [lint-results (eastwood/lint {:source-paths ["src dev"]
                                     :test-paths ["src"]
                                     :add-linters [
                                                   :bad-arglists
                                                   :constant-test
                                                   :def-in-def
                                                   :deprecations
                                                   :keyword-typos
                                                   :local-shadows-var
                                                   :misplaced-docstrings
                                                   :no-ns-form-found
                                                   :non-clojure-file
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
                                                   :wrong-tag
                                                   ]})
        warnings (:warnings lint-results)
        errors (:err lint-results)]
    (println "Linting the code...")
    (if (or (> (count warnings) 0)
            (not= errors nil))
      (do
        (pprint warnings)
        (pprint errors)
        (throw (Exception. "Lint error!")))
      :OK)))
