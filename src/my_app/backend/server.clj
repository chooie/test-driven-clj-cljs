(ns my-app.backend.server
  (:require
   [com.stuartsierra.component :as component]
   [compojure.core :as compojure]
   [compojure.route :as compojure-route]
   [my-app.backend.log :as log])
  (:use [org.httpkit.server :only [run-server]]))

(compojure/defroutes handler
  (compojure/GET "/" [_] (slurp "resources/index.html"))
  (compojure-route/files "" {:root "generated/development"})
  (compojure-route/not-found (slurp "resources/404.html")))

(defrecord Server [port]
  component/Lifecycle

  (start [component]
    (log/info (str "Starting server on port " port "..."))
    (let [server (run-server handler {:port port})]
      (assoc component :server server)))

  (stop [component]
    (log/info "Stopping server")
    (let [server (:server component)]
      (log/info server)
      (if server
        (do
          (server)
          (assoc component :server nil))
        (log/info "Server already stopped! Doing nothing.")))))

(defn new-server [port]
  (map->Server {:port port}))
