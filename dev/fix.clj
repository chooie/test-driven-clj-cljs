(ns fix)

(defn reload []
  (load-file "src/test_lein/core.clj")
  (load-file "dev/user.clj"))
