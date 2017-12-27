(ns my-app.smoke-test
  #_(:require
   [clojure.test :as _test]
   )
  #_(:import
   [org.openqa.selenium.By]
   [org.openqa.selenium.firefox.FirefoxDriver]
   ))

#_(System/setProperty "webdriver.chrome.driver" "binaries/chromedriver")
#_(System/setProperty "webdriver.gecko.driver" "binaries/geckodriver")

#_(test/deftest smoke-test
  (let [_driver (org.openqa.selenium.firefox.FirefoxDriver.)]
    (println "poop")
    (test/is (= false))
    #_(.get driver "https://google.com")
    #_(let [element (.findElement driver (.name org.openqa.selenium.By "q"))]
      #_(.sendKeys element "ChromeDriver")
      #_(.submit element)
      (println "poop"))))
