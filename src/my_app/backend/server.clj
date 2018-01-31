(ns my-app.backend.server
  (:require
   [com.stuartsierra.component :as component]
   [compojure.core :as compojure]
   [compojure.route :as compojure-route]
   [my-app.backend.log :as log]
   [my-app.backend.pages :as my-app-pages])
  (:use [org.httpkit.server :only [run-server]]))

(defn- get-root-by-profile
  [profile]
  (condp = profile
    :test-automation "generated/automated-testing/"
    :development "generated/development/"
    :production "generated/production/"))

(defn create-handler
  [profile]
  (compojure/routes
   (compojure/GET "/" [_] (my-app-pages/index profile))
   (compojure-route/files "" {:root (get-root-by-profile profile)})
   (compojure-route/files "" {:root "resources/static/"})
   (compojure/GET "/*" [_] (my-app-pages/index profile))))

(defrecord Server [port profile]
  component/Lifecycle

  (start [component]
    (log/info (str "Starting server on port " port "..."))
    (let [server (run-server (create-handler profile) {:port port})]
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

(defn new-server [port profile]
  (map->Server {:port port
                :profile profile}))
