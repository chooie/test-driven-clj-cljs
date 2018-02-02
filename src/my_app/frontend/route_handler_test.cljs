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
  (test/testing "Can navigate predictably"
    (route-handler/setup example-routes)
    (test/is (= {:current-page :index
                 :route-params nil}
                (route-handler/get-route-state))
             "Home route gives us the index")

    (route-handler/navigate-to-route "/section-a")
    (test/is (= {:current-page :section-a
                 :route-params nil}
                (route-handler/get-route-state))
             "Should be at section-a")

    (route-handler/navigate-to-route "/section-a/item-1")
    (test/is (= {:current-page :a-item
                 :route-params {:item-id "1"}}
                (route-handler/get-route-state))
             "Should be at item-1 of section-a")

    (route-handler/navigate-to-route "/non-existant-route")
    (test/is (= {:current-page :four-o-four
                 :route-params nil}
                (route-handler/get-route-state))
             "Get 404 for non-existant-route")))
