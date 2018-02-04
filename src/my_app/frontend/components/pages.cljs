(ns my-app.frontend.components.pages
  (:require
   [my-app.common.log :as log]
   [my-app.frontend.components.common :as common]
   [my-app.frontend.components.navbar :as navbar]
   [my-app.frontend.routes :as routes]
   [my-app.frontend.routing :as routing]))

(defmulti page-contents identity)

(defmethod page-contents :index []
  [:span
   [:h1 "Routing example: Index"]
   [navbar/component]])

(defn make-link-item
  [item-id]
  [:li {:name (str "item-" item-id)
        :key (str "item-" item-id)}
   [common/link
    (routing/get-url-for-route routes/app :a-item :item-id item-id)
    (str "Item: " item-id)]])

(defmethod page-contents :section-a []
  [:span
   [:h1 "Routing example: Section A"]
   [:ul
    (map make-link-item (range 1 6))]])

(defn link-page
  [item]
  [:span
   [:h1 (str "Routing example: Section A, item " item)]
   [common/link
    (routing/get-url-for-route routes/app :section-a)
    "Back to Section A"]])

(defmethod page-contents :a-item []
  (let [routing-data (routing/get-route-state)
        item (get-in routing-data [:route-params :item-id])]
    [link-page item]))

(defmethod page-contents :section-b []
  [:span
   [:h1 "Routing example: Section B"]])

(defmethod page-contents :four-o-four []
  "Non-existing routes go here"
  [:span
   [:h1 "404: It is not here"]
   [:p (str "What you are looking for, I do not have. How could I have, what "
            "does not exist?")]])

(defmethod page-contents :default []
  "Configured routes, missing an implementation, go here"
  [:span
   [:h1 "404: My bad"]
   [:p "This page should be here, but I never created it."]])
