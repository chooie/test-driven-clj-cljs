(ns my-app.smoke-test
  (:require
   [clj-webdriver.taxi :as driver]
   [my-app.backend.config :as config]
   [my-app.backend.test-automation-system :as test-automation-system]
   [my-app.common.util :as util]
   ))

(defn- useFirefoxDriver
  []
  (System/setProperty "webdriver.chrome.driver" "binaries/chromedriver-2.35")
  (driver/set-driver! {:browser :chrome}))

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
   (util/string-contains-substring? actual-text expected-text)
   (str "The correct text is displayed on the page\n"
        "Expected: " expected-text "\n"
        "Actual: " actual-text)))

(defn- assert-text-in-page-source
  [text-to-find]
  (assert-text-matches text-to-find (driver/page-source)))

(defn- assert-text-in-element-yet-to-be-rendered
  [text element-query]
  (driver/wait-until #(driver/present? element-query))
  (assert-text-matches text (driver/text element-query)))

(defn check-browser-loads-test-page []
  (try
    (start-system :test-automation)
    (navigate-to-homepage :test-automation)
    (assert-text-in-page-source "This is my test app smoke marker")
    (assert-text-in-element-yet-to-be-rendered "Hello, world!" "#rendered-app")
    (finally
      (test-automation-system/stop))))

(defn check-browser-loads-dev-page []
  (try
    (start-system :development)
    (navigate-to-homepage :development)
    (assert-text-in-page-source "This is my dev app smoke marker")
    (assert-text-in-element-yet-to-be-rendered "Hello, world!" "#rendered-app")
    (finally
      (test-automation-system/stop))))

(defn run-tests []
  (try
    (useFirefoxDriver)
    (check-browser-loads-test-page)
    (check-browser-loads-dev-page)
    (finally
      (driver/quit))))
