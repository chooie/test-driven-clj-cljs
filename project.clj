(defproject test-lein "0.1.0-SNAPSHOT"
  :description "Just trying things out"
  :url "http://my-test-app.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[com.stuartsierra/component "0.3.2"]
                 [org.clojure/clojure "1.8.0"]
                 [ring "1.6.2"]]
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[alembic "0.3.2"]
                                  [org.clojure/tools.namespace "0.2.11"]]}
             :uberjar {:aot :all}}
  :plugins [[lein-eftest "0.3.1"]]
  :eftest {:multithread? false}
  :main ^:skip-aot test-lein.core
  :repl-options {:init-ns user}
  :target-path "target/%s"
  :test-paths ["test" "src/"])
