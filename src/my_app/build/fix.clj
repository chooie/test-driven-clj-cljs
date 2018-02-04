(ns my-app.build.fix
  (:require
   [boot.core :as boot]
   [clojure.tools.namespace.repl :as tools-namespace-repl]
   ))

(defn refresh [options]
  (let [directories (boot/get-env :directories)
        refresh-all? (get options :refresh-all?)
        after-fn (get options :function-to-run-after-refresh)]
    (when refresh-all?
      (tools-namespace-repl/clear))
    (apply tools-namespace-repl/set-refresh-dirs directories)
    (if after-fn
      (tools-namespace-repl/refresh :after after-fn)
      (tools-namespace-repl/refresh))))
