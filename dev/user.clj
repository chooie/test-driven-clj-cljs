(ns user
  (:require
   [alembic.still :as alembic]
   [clojure.tools.namespace.repl :as tools-namespace-repl :refer [refresh]]
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
