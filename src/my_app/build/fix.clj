(ns my-app.build.fix
  (:require
   [boot.core :as boot]
   [clojure.tools.namespace.repl :as tools-namespace-repl]
   ))

(defn refresh [options]
  (tools-namespace-repl/clear)
  (let [directories (boot/get-env :directories)
        after-fn (get options :function-to-run-after-refresh)]
    (apply tools-namespace-repl/set-refresh-dirs directories)
    (if after-fn
      (tools-namespace-repl/refresh :after after-fn)
      (tools-namespace-repl/refresh))))
