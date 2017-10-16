(ns user
  (:require
   [clojure.tools.namespace.repl :refer [refresh]]
   [alembic.still :as alembic]))

(defn reload-dependencies
  []
  (alembic/load-project))
