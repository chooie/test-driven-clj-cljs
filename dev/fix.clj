(ns fix
  (:require
   [clojure.tools.namespace.repl :as tools-namespace-repl]))

(defn reload []
  (load-file "src/test_lein/core.clj")
  (load-file "dev/user.clj"))

(defn refresh []
  (tools-namespace-repl/clear)
  (tools-namespace-repl/refresh))
