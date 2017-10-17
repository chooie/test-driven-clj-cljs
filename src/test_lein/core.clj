(ns test-lein.core
  (:require
   [com.stuartsierra.component :as component]
   [ring.adapter.jetty :as ring-jetty]
   [test-lein.log :as log]
   [taoensso.timbre :as timbre]
   [clojure.test :as test])
  (:gen-class))

(log/set-logging-level :warn)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (timbre/info "Hello, World!"))

(defn hello
  []
  "Hello, world!")

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello, World!"})

(defrecord Server [port]
  component/Lifecycle

  (start [component]
    (timbre/info "Starting Server...")
    (let [server (ring-jetty/run-jetty handler {:port port
                                                :join? false})]
      (assoc component :server server)))

  (stop [component]
    (timbre/info "Stopping server")
    (let [server (:server component)]
      (timbre/info server)
      (.stop server)
      (assoc component :server nil))))

(defn new-server [port]
  (map->Server {:port port}))

(defn system [config-options]
  (component/system-map
   :server (new-server (:port config-options))))
