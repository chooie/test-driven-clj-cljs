(ns my-app.backend.core-test
  (:require
   [clj-http.client :as clj-http-client]
   [clojure.string :as string]
   [clojure.test :as test]
   [my-app.backend.config :as config]
   [my-app.backend.test-automation-system :as test-automation-system]
   ))

(defn fixture
  [f]
  (test-automation-system/init)
  (test-automation-system/start)
  (f)
  (test-automation-system/stop))

(test/use-fixtures :once fixture)

(test/deftest core-smoke-test
  (test/testing "Can get html from the server"
    (let [config (config/get-config-for :test-automation)
          server-response (clj-http-client/get
                           (config/get-fully-qualified-url config))
          response-body (:body server-response)]
      (test/is (string/includes? response-body "This is my app")))))
