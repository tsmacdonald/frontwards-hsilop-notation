(ns frontwards-hsilop-notation.core
  (:require [clojure.string :as str]))

(def operators
  {"+" +
   "-" -
   "/" /
   "*" *})

(def operator? (set (keys operators)))

(def numeric? (partial re-matches #"(\d*\.)?\d+"))

(def parse-number read-string)

(defn- invoke-operator
  [operator [rh lh & rest]]
  (when-not (and rh lh)
    (throw (IllegalArgumentException. "Not enough juice in the stack")))
  (conj rest
        (operator lh rh)))

(defn eval-rpn
  ([tokens] (eval-rpn tokens (list)))
  ([[token & rest] stack]
   (if token
     (cond
       (operator? token) (eval-rpn rest
                                   (invoke-operator (operators token) stack))
       (numeric? token)  (eval-rpn rest
                                   (conj stack (parse-number token)))
       :else             (throw
                          (IllegalArgumentException. (str "Unexpected token: " (prn-str token)))))
     stack)))

(defn eval-string
  [tokens-as-string]
  (eval-rpn (str/split (str/trim tokens-as-string) #"\s+")))
