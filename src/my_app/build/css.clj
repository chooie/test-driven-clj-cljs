(ns my-app.build.css
  (:require
   [my-app.build.config :as config]
   [my-app.build.util :as util]
   [my-app.common.css.core :as css]))

(defn- build-css-file-to-directory
  [directory-path content]
  (util/delete-file-or-directory directory-path)
  (util/create-directory directory-path)
  (spit (str directory-path "main.css") content))

(defn build
  [profile]
  (println "Building css for '" profile "' profile...")
  (let [styles (css/generate-styles)]
    (condp = profile
      :test-automation (build-css-file-to-directory
                        config/automated-testing-css-directory styles)
      :development (build-css-file-to-directory
                    config/dev-css-directory styles)
      :production (build-css-file-to-directory
                   config/production-css-directory styles)
      (throw (Error. (str "Unrecognized profile, '" profile "'"))))))
