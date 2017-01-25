(ns webapp.util)

(defn dump-handler-response [handler]
  (fn [request]
    (let [response (handler request)]
      (println "<== RESPONSE" response)
      response)))

(defn dump-handler-request [handler]
  (fn [request]
    (println "==> REQUEST" request)
    (handler request)))

(defn apply-if [fn pred arg]
  (if (pred arg) (fn arg) arg))

(defn safe [fn arg]
  (apply-if fn some? arg))

