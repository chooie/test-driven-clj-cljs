(ns my-app.smoke-test
  (:require
   [clj-webdriver.taxi :as driver]
   [my-app.backend.config :as config]
   [my-app.backend.test-automation-system :as test-automation-system]
   ))



(System/setProperty "webdriver.gecko.driver" "binaries/geckodriver")

(defn check-browser-loads-page []
  (driver/set-driver! {:browser :firefox})
  (try
    (test-automation-system/init)
    (test-automation-system/start)
    (driver/to
     (config/get-fully-qualified-url
      (config/get-config-for
       :test-automation)))
    (let [expected-text "This is my app"
          actual-text (driver/text "#app-declaration")]
      (assert
       (= expected-text actual-text)
       (str "The correct text is displayed on the page\n"
            "Expected: " expected-text "\n"
            "Actual: " actual-text)))
    (finally
      (test-automation-system/stop)
      (driver/quit))))
