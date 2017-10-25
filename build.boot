(set-env!
 :resource-paths #{"src" "resources"}
 :dependencies '[
                 [clj-http "3.7.0"]
                 [com.stuartsierra/component "0.3.2"]
                 [com.taoensso/timbre "4.10.0"]
                 [eftest "0.3.2"]
                 [me.raynes/fs "1.4.6"]
                 [org.clojure/clojure "1.9.0-beta2"]
                 [org.clojure/clojurescript "1.9.946"]
                 [ring "1.6.2"]

                 ;; Dev/Test
                 [jonase/eastwood "0.2.5"
                  :exclusions [org.clojure/clojure]
                  :scope "test"
                  ]
                 [jonase/kibit "0.1.5" :scope "test"]
                 [org.clojure/tools.namespace "0.2.11" :scope "test"]
                 ])

(require
 '[my-app.build.dev :as dev])
