(ns test-lein.core
  (:require
   [com.stuartsierra.component :as component]
   [ring.adapter.jetty :as ring-jetty]
   [test-lein.log :as log])
  (:gen-class))

(log/set-logging-level :warn)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (log/info "Hello, World!"))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello, World!"})

(defrecord Server [port]
  component/Lifecycle

  (start [component]
    (log/info "Starting Server...")
    (let [server (ring-jetty/run-jetty handler {:port port
                                                :join? false})]
      (assoc component :server server)))

  (stop [component]
    (log/info "Stopping server")
    (let [server (:server component)]
      (log/info server)
      (.stop server)
      (assoc component :server nil))))

(defn new-server [port]
  (map->Server {:port port}))

(defn system [config-options]
  (component/system-map
   :server (new-server (:port config-options))))
