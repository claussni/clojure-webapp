(ns webapp.handler
  (:import (java.util UUID))
  (:require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
    [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
    [ring.util.response :refer [response created]]
    [webapp.things :as things]
    [webapp.util :refer :all]))

(defroutes app-routes
           (GET "/" [] "World of Things")
           (context "/things" []
             (GET "/" [] (safe response (things/find-all)))
             (GET "/:id" [id] (safe response (things/find-by-id id)))
             (POST "/" {body :body context :context}
               (let [newid (str (UUID/randomUUID))]
                 (things/new newid)
                 (created (str context "/" newid) body))))
           (route/not-found "Not Found"))

(def app
  (-> app-routes
      (dump-handler-request)
      (wrap-json-body)
      (wrap-json-response)
      (wrap-defaults api-defaults)
      (dump-handler-response)))
