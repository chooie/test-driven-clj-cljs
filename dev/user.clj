(ns user
  (:require
   [alembic.still :as alembic]
   [clojure.pprint :refer [pprint]]
   [clojure.test :as clojure-test]
   [clojure.tools.namespace.repl :as tools-namespace-repl]
   [com.stuartsierra.component :as component]
   [test-lein.core :as test-lein]))

(defn reload-dependencies
  []
  (alembic/load-project))

(def system nil)

(defn init
  []
  (alter-var-root
   #'system
   (constantly (test-lein/system {:port 3000}))))

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
      (let [results (clojure-test/run-all-tests #"test-lein.*-test")
            number-of-fails (:fail results)]
        (if (> number-of-fails 0)
          (throw (Exception. (str "FAIL: " number-of-fails)))
          "."))
      (throw refresh-result))))
