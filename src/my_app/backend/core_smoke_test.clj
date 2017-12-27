(ns my-app.backend.core-smoke-test
  (:require
   [clj-http.client :as clj-http-client]
   [clojure.string :as string]
   [clojure.test :as test]
   [com.stuartsierra.component :as component]
   [my-app.backend.config :as config]
   [my-app.backend.core :as my-app]))

;; TODO: Should get this test code running in the browser
(def system nil)

(defn init
  []
  (alter-var-root
   #'system
   (constantly (my-app/system :test-automation))))

(defn start []
  (alter-var-root #'system component/start))

(defn stop []
  (alter-var-root
   #'system
   (fn [system] (when system (component/stop system)))))

(defn fixture
  [f]
  (init)
  (start)
  (f)
  (stop))

(test/use-fixtures :once fixture)

(test/deftest core-smoke-test
  (let [config (config/get-config-for :test-automation)
        server-response (clj-http-client/get
                         (config/get-fully-qualified-url config))
        response-body (:body server-response)]
    (test/testing "Can get html from the server"
      (test/is (string/includes? response-body "This is my app")))))
