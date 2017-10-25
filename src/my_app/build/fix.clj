(ns my-app.build.fix
  (:require
   [boot.core :as boot]
   [clojure.tools.namespace.repl :as tools-namespace-repl]
   ))

(defn refresh [& args]
  (tools-namespace-repl/clear)
  (let [directories (boot/get-env :directories)]
    (apply tools-namespace-repl/set-refresh-dirs directories)
    (if (= (count args) 1)
      (tools-namespace-repl/refresh :after (first args))
      (tools-namespace-repl/refresh))))
