(ns my-app.build.config)

(def generated-directory "generated/")

(def automated-testing-directory (str generated-directory "automated-testing/"))

(def dev-directory (str generated-directory "development/"))

(def production-directory (str generated-directory "production/"))

(def automated-testing-css-directory (str automated-testing-directory "css/"))

(def dev-css-directory (str dev-directory "css/"))

(def production-css-directory (str production-directory "css/"))
