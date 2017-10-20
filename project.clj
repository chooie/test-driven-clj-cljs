(defproject test-lein "0.1.0-SNAPSHOT"
  :description "Just trying things out"
  :url "http://my-test-app.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-http "3.7.0"]

                 [com.stuartsierra/component "0.3.2"]
                 [com.taoensso/timbre "4.10.0"]
                 [org.clojure/clojure "1.9.0-beta2"]
                 [ring "1.6.2"]]
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[alembic "0.3.2"]
                                  [org.clojure/tools.namespace "0.2.11"]
                                  [pjstadig/humane-test-output "0.8.3"]]
                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]}
             :uberjar {:aot :all}}
  :plugins [[lein-eftest "0.3.1"]]
  :eftest {:multithread? false}
  :main ^:skip-aot test-lein.core
  :repl-options {:init-ns user}
  :target-path "target/%s"
  :test-paths ["src/"])
