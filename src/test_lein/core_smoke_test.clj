(ns test-lein.core-smoke-test
  (:require
   [clojure.test :as test]
   [com.stuartsierra.component :as component]
   [test-lein.core :as test-lein]))

(def system nil)

(defn init
  []
  (alter-var-root
   #'system
   (constantly (test-lein/system {:port 8081}))))

(defn start []
  (alter-var-root #'system component/start))

(defn stop []
  (alter-var-root
   #'system
   (fn [system] (when system (component/stop system)))))

(defn fixture
  [f]
  (init)
  (start)
  (f)
  (stop))

(test/use-fixtures :once fixture)

(test/deftest core-smoke-test
  (test/is (= true false)))
