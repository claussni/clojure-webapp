(ns webapp.handler
  (:require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
    [ring.middleware.json :refer [wrap-json-response]]
    [ring.util.response :refer [response]]
    [webapp.things :as things]
    ))

(defn apply-if [fn pred arg]
  (if (pred arg) (fn arg) arg))

(defn safe-response [body]
  (apply-if response some? body))

(defroutes app-routes
           (GET "/" [] "World of Things")
           (context "/things" []
             (GET "/" [] (safe-response (things/find-all)))
             (GET "/:id" [id] (safe-response (things/find-by-id id))))
           (route/not-found "Not Found"))

(defn dump-handler-response [handler]
  (fn [request]
    (let [response (handler request)]
      (println response)
      response)))

(def app
  (-> app-routes
      (dump-handler-response)
      (wrap-json-response)
      (wrap-defaults api-defaults)
      ))
