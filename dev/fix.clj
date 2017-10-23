(ns fix
  (:require
   [clojure.tools.namespace.repl :as tools-namespace-repl]))

;; NOTE: In case of emergency
;; (load-file "dev/fix.clj")

(defn reload []
  (load-file "src/my_app/core.clj")
  (load-file "dev/user.clj"))

(defn refresh []
  (tools-namespace-repl/clear)
  (tools-namespace-repl/refresh))
