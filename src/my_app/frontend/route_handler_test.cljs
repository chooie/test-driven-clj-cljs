(ns my-app.frontend.route-handler-test
  (:require
   [cljs.test :as test :include-macros true]
   [my-app.frontend.route-handler :as route-handler]))

(def example-routes
  ["/"
   {;; Karma debugs in the page '/context.html'
    "context.html" :index
    "section-a" {"" :section-a
                 ["/item-" :item-id] :a-item}
    "section-b" :section-b
    "missing-route" :missing-route
    true :four-o-four}])

(test/deftest route-handler-test
  (test/testing "Home route gives us the index"
    (route-handler/setup example-routes)
    (test/is (= {:current-page :index
                 :route-params nil}
                (route-handler/get-route-state)))))
