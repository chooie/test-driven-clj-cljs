(ns my-app.backend.server
  (:require
   [com.stuartsierra.component :as component]
   [my-app.backend.log :as log])
  (:use [org.httpkit.server :only [run-server]]))

(defn handler [request]
  (let [uri (get request :uri)]
    (if (= uri "/")
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (slurp "resources/index.html")}
      {:status 404
       :headers {"Content-Type" "text/html"}
       :body (slurp "resources/404.html")})))

(defrecord Server [port]
  component/Lifecycle

  (start [component]
    (log/info (str "Starting server on port " port "..."))
    (let [server (run-server handler {:port port
                                      :join? false})]
      (assoc component :server server)))

  (stop [component]
    (log/info "Stopping server")
    (let [server (:server component)]
      (log/info server)
      (server)
      (assoc component :server nil))))

(defn new-server [port]
  (map->Server {:port port}))
