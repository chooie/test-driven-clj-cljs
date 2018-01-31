(ns my-app.common.error)

(defn throw
  [message]
  #?(:clj (throw (Exception. message))
     :cljs (throw (js/Error. message))))
