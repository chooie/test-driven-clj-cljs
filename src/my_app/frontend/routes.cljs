(ns my-app.frontend.routes)

(def app
  ["/" {"" :index
        "section-a" {"" :section-a
                     ["/item-" :item-id] :a-item}
        "section-b" :section-b
        "missing-route" :missing-route
        true :four-o-four}])
