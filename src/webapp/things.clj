(ns webapp.things
  (:import (java.util UUID)))

(def -things
  [{:id "thing1"}
   {:id "thing2"}
   {:id "thing3"}])

(defn find-all []
  -things)

(defn find-by-id [id]
  (some #(if (= (:id %) id) %) -things))

(defn new [body]
  (println "new body: " body)
  (str (UUID/randomUUID)))
