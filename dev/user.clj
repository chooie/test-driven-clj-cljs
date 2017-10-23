(ns user
  (:require
   [alembic.still :as alembic]
   [clojure.pprint :refer [pprint]]
   [clojure.test :as clojure-test]
   [clojure.tools.namespace.repl :as tools-namespace-repl]
   [com.stuartsierra.component :as component]
   [eftest.runner :as eftest]
   [idiomatic :as idiomatic]
   [lint :as lint]
   [pjstadig.humane-test-output :as humane-test-output]
   [test-lein.backend.core :as test-lein]
   [test-lein.backend.log :refer [set-logging-level!]]))

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

(defn run-tests
  []
  (let [results (eftest/run-tests (eftest/find-tests "src"))
        number-of-fails (:fail results)]
    (if (pos? number-of-fails)
      (throw (Exception. (str "FAIL: " number-of-fails)))
      :OK)))

(defn t []
  (let [refresh-result (safe-refresh)]
    (if (= refresh-result :ok)
      (do
        (lint/lint)
        (run-tests))
      (throw refresh-result))))

(defn analyse []
  (let [refresh-result (safe-refresh)]
    (if (= refresh-result :ok)
      (idiomatic/analyse)
      (throw refresh-result))))
