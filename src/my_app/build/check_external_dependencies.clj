(ns my-app.build.check-external-dependencies
  (:require
   [clojure.java.shell :as clojure-java-shell]))

(clojure-java-shell/sh
 "node" "--version")
