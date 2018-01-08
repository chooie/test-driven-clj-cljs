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
  [profile]
  (test-automation-system/init profile)
  (test-automation-system/start))

(defn- navigate-to-homepage
  [profile]
  (driver/to
   (config/get-fully-qualified-url
    (config/get-config-for
     profile))))

(defn- assert-text-matches
  [expected-text actual-text]
  (assert
       (= expected-text actual-text)
       (str "The correct text is displayed on the page\n"
            "Expected: " expected-text "\n"
            "Actual: " actual-text)))

(defn check-browser-loads-test-page []
  (try
    (useFirefoxDriver)
    (start-system :test-automation)
    (navigate-to-homepage :test-automation)
    (let [expected-text "This is my app"
          actual-text (driver/text "#app-declaration")]
      (assert-text-matches expected-text actual-text))
    (finally
      (test-automation-system/stop)
      (driver/quit))))

(defn check-browser-loads-dev-page []
  (try
    (useFirefoxDriver)
    (start-system :development)
    (navigate-to-homepage :development)
    (let [expected-text "This is my dev app"
          actual-text (driver/text "#app-declaration")]
      (assert-text-matches expected-text actual-text))
    (finally
      (test-automation-system/stop)
      (driver/quit))))

(defn run-tests []
  (try
    (check-browser-loads-test-page)
    (check-browser-loads-dev-page)))
