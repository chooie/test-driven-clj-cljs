(ns my-app.boot.user
  (:require
   [clojure.pprint :refer [pprint]]
   [com.stuartsierra.component :as component]
   [my-app.backend.core :as my-app]
   [my-app.boot.fix :as fix]
   [my-app.boot.idiomatic :as idiomatic]
   [my-app.boot.lint :as lint]
   [my-app.boot.test :as tester]
   ))

(def system nil)

(defn show-system []
  (clojure.pprint/pprint system))

(defn init
  []
  (alter-var-root
   #'system
   (constantly (my-app/system "development"))))

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
  (fix/refresh 'my-app.boot.user/go))

(defn safe-refresh
  "It's important that we stop the component before refreshing all the
  namespaces because otherwise the server will still be running in the
  background and we'll have lost the reference. This will then cause
  a resource in use error when we want to start the component up again"
  []
  (stop)
  (fix/refresh))

(defn t []
  (let [refresh-result (safe-refresh)]
    (if (= refresh-result :ok)
      (do
        (lint/lint)
        (tester/run-tests))
      (throw refresh-result))))

(defn analyse []
  (let [refresh-result (safe-refresh)]
    (if (= refresh-result :ok)
      (idiomatic/analyse)
      (throw refresh-result))))
