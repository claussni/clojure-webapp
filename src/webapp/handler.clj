(ns webapp.handler
  (:require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
    [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
    [ring.util.response :refer [response created]]
    [webapp.things :as things]))

(defn apply-if [fn pred arg]
  (if (pred arg) (fn arg) arg))

(defn safe [fn arg]
  (apply-if fn some? arg))

(defroutes app-routes
           (GET "/" [] "World of Things")
           (context "/things" []
             (GET "/" [] (safe response (things/find-all)))
             (GET "/:id" [id] (safe response (things/find-by-id id)))
             (POST "/" {body :body context :context}
               (created (str context "/" (things/new body)) body)))
           (route/not-found "Not Found"))

(defn dump-handler-response [handler]
  (fn [request]
    (let [response (handler request)]
      (println "<== RESPONSE" response)
      response)))

(defn dump-handler-request [handler]
  (fn [request]
    (println "==> REQUEST" request)
    (handler request)))

(def app
  (-> app-routes
      (dump-handler-request)
      (wrap-json-body)
      (wrap-json-response)
      (wrap-defaults api-defaults)
      (dump-handler-response)
      ))
