(ns my-app.boot-tasks.core
  (:require
   [boot.core :as boot]
   [boot.task.built-in :as boot-tasks]
   [my-app.build.client :as client]
   [my-app.build.dev :as dev]
   ))

(defn reloadable-task [callback]
  (boot/with-pass-thru _
    (with-bindings {#'*ns* *ns*}
      (dev/safe-refresh)
      (callback))))

(boot/deftask quick-check []
  "Lint and run tests"
  (reloadable-task dev/check))

(boot/deftask analyse []
  "Perform idiomatic code analysis"
  (reloadable-task
   (fn []
     (dev/analyse))))

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

(boot/deftask build []
  (reloadable-task
   (fn []
     (client/build-cljs))))
