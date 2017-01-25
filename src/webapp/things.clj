(ns webapp.things)

(def things (atom []))

(defn find-all [] @things)

(defn find-by-id [id]
  (some #(if (= (:id %) id) %) @things))

(defn new [id]
  (swap! things concat [{:id id}]))
