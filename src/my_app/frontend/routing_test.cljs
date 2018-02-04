(ns my-app.frontend.routing-test
  (:require
   [cljs.test :as test :include-macros true]
   [my-app.frontend.routing :as routing]))

(def example-routes
  ["/"
   {;; Karma debugs in the page '/context.html'
    "context.html" :index
    "section-a" {"" :section-a
                 ["/item-" :item-id] :a-item}
    "section-b" :section-b
    "missing-route" :missing-route
    true :four-o-four}])

(test/deftest routing-test
  (routing/setup example-routes)

  (test/testing "Can navigate predictably"
    (test/is (= {:current-page :index
                 :route-params nil}
                (routing/get-route-state))
             "Home route gives us the index")

    (routing/navigate-to-route "/section-a")
    (test/is (= {:current-page :section-a
                 :route-params nil}
                (routing/get-route-state))
             "Should be at section-a")

    (routing/navigate-to-route "/section-a/item-1")
    (test/is (= {:current-page :a-item
                 :route-params {:item-id "1"}}
                (routing/get-route-state))
             "Should be at item-1 of section-a")

    (routing/navigate-to-route "/non-existant-route")
    (test/is (= {:current-page :four-o-four
                 :route-params nil}
                (routing/get-route-state))
             "Get 404 for non-existant-route"))

  (test/testing "Can create urls that correspond to the correct route"
    (test/is (= "/context.html"
                (routing/get-url-for-route example-routes :index))
             "Get correct index page url")
    (test/is (thrown?
              js/Error
              (routing/get-url-for-route example-routes :no-idea))
             "An error is thrown for invalid routes")
    (test/is (= "/section-a/item-1"
                (routing/get-url-for-route example-routes :a-item :item-id 1))
             "Nested routes resolve correctly")))
