(ns webapp.things-test
  (:require [clojure.test :refer :all]
            [webapp.things :as things]))

(deftest test-things
  (testing "Find thing which has been added before"
    (things/new "thing1")
    (let [response (things/find-by-id "thing1")]
      (is (= (:id response) "thing1")))))
