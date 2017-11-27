(ns my-app.boot-tasks.core
  (:require
   [adzerk.boot-cljs :as boot-cljs]
   [adzerk.boot-cljs-repl :as boot-cljs-repl]
   [boot.core :as boot]
   [boot.task.built-in :as boot-tasks]
   [my-app.build.dev :as dev]
   ))

(defn reloadable-task
  ([]
   ;; Callback does nothing
   (reloadable-task #(identity nil)))

  ([callback]
   (boot/with-pass-thru _
     (with-bindings {#'*ns* *ns*}
       (dev/safe-refresh)
       (callback)))))

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

(boot/deftask start-cljs-repl []
  (comp
   ;; Uncomment this for debugging purposes
   #_(boot-tasks/show :fileset true)
   (boot-tasks/watch)
   (reloadable-task)
   (boot-cljs-repl/cljs-repl)
   (boot-cljs/cljs
    :source-map true
    :optimizations :none
    :compiler-options {:output-dir "generated/"})
   (boot-tasks/target :dir #{"generated/automated-testing"})))

(boot/deftask build-dev []
  (comp
   (boot-tasks/show :fileset true)
   (boot-cljs/cljs
    :source-map true
    :optimizations :none
    :compiler-options {:output-dir "generated/"})
   (boot-tasks/target :dir #{"generated/development"})))

(boot/deftask cider
  "CIDER profile"
  []
  (require 'boot.repl)
  (swap! @(resolve 'boot.repl/*default-dependencies*)
         concat '[[org.clojure/tools.nrepl "0.2.13"]
                  [cider/cider-nrepl "0.16.0-SNAPSHOT"]
                  [refactor-nrepl "2.4.0-SNAPSHOT"]])
  (swap! @(resolve 'boot.repl/*default-middleware*)
         concat '[cider.nrepl/cider-middleware
                  refactor-nrepl.middleware/wrap-refactor])
  identity)

(boot/deftask start-cider-development-repl []
  (comp
   (cider)
   (start-cljs-repl)))
