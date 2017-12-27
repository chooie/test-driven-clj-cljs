(ns my-app.build.dev
  (:require
   [adzerk.boot-cljs-repl :as boot-cljs-repl]
   [clojure.pprint :as clj-pprint :refer [pprint]]
   [com.stuartsierra.component :as component]
   [my-app.backend.core :as my-app]
   [my-app.build.backend-test :as backend-tester]
   [my-app.build.fix :as fix]
   [my-app.build.frontend :as frontend]
   [my-app.build.idiomatic :as idiomatic]
   [my-app.build.lint :as lint]
   [my-app.build.time-reporting :as time-reporting]
   [my-app.smoke-test :as smoke]
   ))

(def system nil)

(defn show-system []
  (clj-pprint/pprint system))

(defn init
  []
  (alter-var-root
   #'system
   (constantly (my-app/system :development))))

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
  (fix/refresh 'my-app.build.dev/go))

(defn safe-refresh
  "It's important that we stop the component before refreshing all the
  namespaces because otherwise the server will still be running in the
  background and we'll have lost the reference. This will then cause
  a resource in use error when we want to start the component up again"
  []
  (let [started-at (time-reporting/get-time-in-ms-now)]
    (stop)
    (fix/refresh)
    (time-reporting/measure-and-report-elapsed-time
     "Reloaded namespaces after: "
     started-at)))

(defn analyse []
  (idiomatic/analyse))

(defn run-smoke-tests []
  (let [started-at (time-reporting/get-time-in-ms-now)]
    (smoke/check-browser-loads-page)
    (time-reporting/measure-and-report-elapsed-time
     "Ran smoke tests after: "
     started-at)))

(defn check []
  (lint/lint)
  (backend-tester/run-tests)
  (frontend/build-cljs)
  (frontend/run-tests-with-karma)
  (run-smoke-tests)
  (println "CHECK OK!"))

(defn t []
  (let [started-at (time-reporting/get-time-in-ms-now)]
    (safe-refresh)
    (check)
    (time-reporting/measure-and-report-elapsed-time
     "Build and check finished after: "
     started-at)))

(defn start-cljs []
  (boot-cljs-repl/start-repl))
