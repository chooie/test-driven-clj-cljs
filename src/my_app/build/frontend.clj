(ns my-app.build.frontend
  (:require
   [cljs.build.api :as cljs-build]
   [clojure.java.io :as io]
   [clojure.java.shell :as clojure-java-shell]
   #_[clojure.string :as string]
   ))

(def generated-directory "generated/")
(def automated-testing-directory (str generated-directory "automated-testing/"))

(declare delete-recursively)
(defn- delete-all-files-in-directory
  [file]
  (doseq [current-file (.listFiles file)]
      (delete-recursively current-file)))

(defn- delete-recursively
  [file]
  (when (.isDirectory file)
    (delete-all-files-in-directory file))
  (io/delete-file file))

(defn delete-file-or-directory
  [path]
  (let [file (io/file path)]
    (when (.exists file)
      (delete-recursively file))))

(defn build-cljs
  []
  (println "Generating frontend code...")
  (delete-file-or-directory automated-testing-directory)
  (cljs-build/build
   "src/karma_cljs"
   {:output-dir automated-testing-directory
    :output-to (str automated-testing-directory "js/karma_cljs.js")})

  (cljs-build/build
   "src/my_app/frontend/"
   {:output-dir automated-testing-directory
    :output-to (str automated-testing-directory "js/my_app.js")}))

(defn run-tests-with-karma
  []
  (println "Running frontend tests...")
  (let [karma-binary-path "./node_modules/karma/bin/karma"
        process-results (clojure-java-shell/sh
                         karma-binary-path
                         "run"
                         "karma.config.js"
                         "--no-colors")
        output (get process-results :out)
        exit-code (get process-results :exit)]
    (println output)
    (when (> exit-code 0)
      (throw (Exception. "Frontend tests failed!")))
    (println "Frontend Tests: OK")))
