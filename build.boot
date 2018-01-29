(defn setup-boot-environment []
  (set-env!
   :resource-paths #{"resources"}
   :source-paths  #{"boot_tasks" "src"}
   :dependencies '[
                   [clj-http "3.7.0"]
                   [clj-time "0.14.2"]
                   [com.stuartsierra/component "0.3.2"]
                   [com.taoensso/timbre "4.10.0"]
                   [compojure "1.6.0"]
                   [eftest "0.3.2"]
                   [garden "1.3.3"]
                   [hiccup "1.0.5"]
                   [http-kit "2.2.0"]
                   [org.clojure/clojure "1.9.0"]
                   [org.clojure/clojurescript "1.9.946"]
                   [reagent "0.8.0-alpha2"]
                   [ring "1.6.3"]

                   ;; Dev/Test
                   [adzerk/boot-cljs "2.1.4" :scope "test"]
                   [adzerk/boot-cljs-repl "0.3.3" :scope "test"]
                   [clj-webdriver "0.7.2" :scope "test"]
                   [com.cemerick/piggieback "0.2.1" :scope "test"]
                   [jonase/eastwood
                    "0.2.5"
                    :exclusions [org.clojure/clojure]
                    :scope "test"
                    ]
                   [jonase/kibit "0.1.5" :scope "test"]
                   [org.clojure/tools.namespace "0.2.11" :scope "test"]
                   [org.clojure/tools.nrepl "0.2.12" :scope "test"]
                   [org.seleniumhq.selenium/selenium-java "3.8.1" :scope "test"]
                   [org.seleniumhq.selenium/selenium-htmlunit-driver
                    "2.52.0"
                    :scope "test"
                    ]
                   [weasel "0.7.0" :scope "test"]
                   ]))

(def stability-of-repo "UNSTABLE")

(defn clear-aliases []
  (ns-unalias 'boot.user 'dev)
  (ns-unalias 'boot.user 'my-app)
  (ns-unalias 'boot.user 'tester))

(defn setup-working-namespaces []
  (println "Setting up working namespaces...")
  (setup-boot-environment)
  (clear-aliases)
  (require
   '[my-app.boot-tasks.core :as my-app]
   '[my-app.build.dev :as dev]
   '[my-app.build.test-running :as tester])
  (println "Ready!"))

(defn show-classpath []
  (let [class-path (get-env :boot-class-path)
        class-path-vector (clojure.string/split class-path #":")]
    (clojure.pprint/pprint class-path-vector)))

(deftask check-conflicts
  "Verify there are no dependency conflicts."
  []
  (with-pass-thru fs
    (require '[boot.pedantic :as pedant])
    (require '[boot.pod :as pod])
    (let [dep-conflicts (resolve 'pedant/dep-conflicts)]
      (if-let [conflicts (not-empty (dep-conflicts (resolve 'pod/env)))]
        (throw (ex-info (str "Unresolved dependency conflicts. "
                             "Use :exclusions to resolve them!")
                        conflicts))
        (println "\nVerified there are no dependency conflicts.")))))

(setup-working-namespaces)

(deftask build
  "This task is run by the boot buildpack for heroku"
  []
  (comp
   (my-app/build-for-production
    :stability stability-of-repo)))
