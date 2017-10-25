(ns my-app.build.user
  (:require
   [boot.core :as boot]
   [boot.task.built-in :as boot-tasks]
   [clojure.pprint :refer [pprint]]
   [com.stuartsierra.component :as component]
   [my-app.backend.core :as my-app]
   [my-app.build.fix :as fix]
   [my-app.build.idiomatic :as idiomatic]
   [my-app.build.lint :as lint]
   [my-app.build.test :as tester]
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

(defn reloadable-task [callback]
  (boot/with-pass-thru _
    (with-bindings {#'*ns* *ns*}
      (safe-refresh)
      (callback))))

(boot/deftask quick-check []
  "Lint and run tests"
  (reloadable-task
   (fn []
     (lint/lint)
     (tester/run-tests))))

(boot/deftask analyse []
  "Perform idiomatic code analysis"
  (reloadable-task
   (fn []
     (idiomatic/analyse))))

(boot/deftask check-all []
  "Analyse, lint, and test"
  (comp
   (analyse)
   (quick-check)))

(boot/deftask watch-check []
  "Repeatedly check code after every update"
  (comp
   (boot-tasks/speak)
   (boot-tasks/watch
    :verbose true)
   (quick-check)))
