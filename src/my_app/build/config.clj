(ns my-app.build.config)

(def generated-directory "generated/")

(def automated-testing-directory (str generated-directory "automated-testing/"))

(def dev-directory (str generated-directory "development/"))

(def automated-testing-css-directory (str automated-testing-directory "css/"))

(def dev-css-directory (str dev-directory "css/"))
