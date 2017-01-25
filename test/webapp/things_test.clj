(ns webapp.things-test
  (:require [clojure.test :refer :all]
            [webapp.things :as things]))

(deftest test-things
  (testing "Find thing"
    (let [response (things/find-by-id "thing1")]
      (is (= (:id response) "thing1")))))
