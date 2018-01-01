(ns my-app.smoke-test
  (:require
   [clj-webdriver.taxi :as driver]
   [my-app.backend.config :as config]
   [my-app.backend.test-automation-system :as test-automation-system]
   ))

(defn- useFirefoxDriver
  []
  (System/setProperty "webdriver.gecko.driver" "binaries/geckodriver")
  (driver/set-driver! {:browser :firefox}))

(defn- start-system
  []
  (test-automation-system/init)
  (test-automation-system/start))

(defn- navigate-to-homepage
  []
  (driver/to
     (config/get-fully-qualified-url
      (config/get-config-for
       :test-automation))))

(defn- assert-text-matches
  [expected-text actual-text]
  (assert
       (= expected-text actual-text)
       (str "The correct text is displayed on the page\n"
            "Expected: " expected-text "\n"
            "Actual: " actual-text)))

(defn check-browser-loads-page []
  (useFirefoxDriver)
  (try
    (start-system)
    (navigate-to-homepage)
    (let [expected-text "This is my app"
          actual-text (driver/text "#app-declaration")]
      (assert-text-matches expected-text actual-text))
    (finally
      (test-automation-system/stop)
      (driver/quit))))
