(ns user
  (:require
   [alembic.still :as alembic]
   [clojure.pprint :refer [pprint]]
   [clojure.test :as clojure-test]
   [clojure.tools.namespace.repl :as tools-namespace-repl]
   [com.stuartsierra.component :as component]
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

(defn t []
  (let [refresh-result (safe-refresh)]
    (if (= refresh-result :ok)
      (let [results (eftest/run-tests (eftest/find-tests "src"))
            number-of-fails (:fail results)]
        (if (> number-of-fails 0)
          (throw (Exception. (str "FAIL: " number-of-fails)))
          :OK))
      (throw refresh-result))))
