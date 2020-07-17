(ns frontwards-hsilop-notation.core-test
  (:require [clojure.test :refer :all]
            [frontwards-hsilop-notation.core :refer :all]))

(deftest a-test
  (testing "operator?"
    (is (operator? "+"))
    (is (operator? "-"))
    (is (operator? "*"))
    (is (operator? "/"))
    (is (false? (operator? "!")))
    (is (false? (operator? "42"))))
  (testing "numeric?"
    (is (numeric? "42"))
    (is (numeric? "3.14"))
    (is (not (numeric? "elevnty")))
    (is (not (numeric? "1."))))
  (testing "invoke-operator"
    (is (= '(42) (invoke-operator * [6 7])))
    (is (= '(42 86) (invoke-operator * [6 7 86])))
    (is (thrown? IllegalArgumentException (invoke-operator + [1])))))
