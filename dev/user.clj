(ns user
  (:require
   [alembic.still :as alembic]
   [clojure.pprint :refer [pprint]]
   [clojure.test :as clojure-test]
   [clojure.tools.namespace.repl :as tools-namespace-repl]
   [com.stuartsierra.component :as component]
   [eastwood.lint :as eastwood]
   [eftest.runner :as eftest]
   [pjstadig.humane-test-output :as humane-test-output]
   [test-lein.core :as test-lein]
   [test-lein.log :refer [set-logging-level!]]))

(defn reload-dependencies
  []
  (alembic/load-project))

(def system nil)

(defn show-system []
  (clojure.pprint/pprint system))

(defn init
  []
  (alter-var-root
   #'system
   (constantly (test-lein/system "development"))))

(defn start []
  (alter-var-root #'system component/start))

(defn stop []
  (alter-var-root
   #'system
   (fn [system] (when system (component/stop system)))))

(defn go []
  (init)
  (start))

(defn reset []
  (stop)
  (tools-namespace-repl/refresh :after 'user/go))

(defn safe-refresh []
  (stop)
  (tools-namespace-repl/refresh))

(defn lint []
  (let [lint-results (eastwood/lint {:source-paths ["src dev"]
                                     :test-paths ["src"]})
        warnings (:warnings lint-results)
        errors (:err lint-results)]
    (println "Linting the code...")
    (if (or (> (count warnings) 0)
            (not= errors nil))
      (throw (Exception. "Lint error!"))
      :OK)))

(defn run-tests
  []
  (let [results (eftest/run-tests (eftest/find-tests "src"))
        number-of-fails (:fail results)]
    (if (> number-of-fails 0)
      (throw (Exception. (str "FAIL: " number-of-fails)))
      :OK)))

(defn t []
  (let [refresh-result (safe-refresh)]
    (if (= refresh-result :ok)
      (do
        (lint)
        (run-tests))
      (throw refresh-result))))
