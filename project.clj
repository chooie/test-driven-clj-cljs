(defproject test-lein "0.1.0-SNAPSHOT"
  :description "Just trying things out"
  :url "http://my-test-app.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :plugins [[lein-eftest "0.3.1"]]
  :main ^:skip-aot test-lein.core
  :target-path "target/%s"
  :test-paths ["test" "src/"]
  :profiles {:uberjar {:aot :all}})
