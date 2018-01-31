(ns my-app.boot-tasks.core
  (:require
   [adzerk.boot-cljs :as boot-cljs]
   [adzerk.boot-cljs-repl :as boot-cljs-repl]
   [boot.core :as boot]
   [boot.task.built-in :as boot-tasks]
   [clj-time.core :as clj-time]
   [my-app.build.check :as check]
   [my-app.build.css :as css]
   [my-app.build.config :as config]
   ))

;; NOTE: This is kept out of the 'src' directory because this namespace seems
;; to break the Eastwood linter

(boot/deftask start-development-repl []
  (comp
   (boot-tasks/watch)
   (boot-cljs-repl/cljs-repl)
   (boot-cljs/cljs
    :source-map true
    :optimizations :none)
   (boot-tasks/target :dir #{(str config/generated-directory "development")})))

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
   (start-development-repl)))

(boot/deftask build-production-cljs []
  (comp
   (boot-cljs/cljs
    :optimizations :advanced)
   (boot-tasks/target :dir #{config/production-directory})))

(boot/deftask build-for-production
  "Builds an uberjar of this project that can be run with java -jar"
  [s stability VAL str "How stable the repo is"]
  (comp
   (boot/with-pass-thru _
     (check/lint-and-full-backend-check))
   (build-production-cljs)
   (boot/with-pass-thru _
     (css/build :production))
   (boot-tasks/pom
    :project 'my-app
    :version (str
              "not-a-real-git-hash-yet | "
              (clj-time/now)
              " | "
              stability)
    :description "Application template with a heavy focus on TDD"
    :license {"The MIT License (MIT)" "See LICENSE.txt"})
   (boot-tasks/aot
    :namespace #{'my-app.backend.entry-point})
   (boot-tasks/uber)
   (boot-tasks/jar
    :file "my-app.jar"
    :main 'my-app.backend.entry-point)
   (boot-tasks/target
    :dir #{"release"})))
