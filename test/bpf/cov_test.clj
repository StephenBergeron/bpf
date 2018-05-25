(ns bpf.cov-test
  (:require [bpf.cov :as sut]
            [clojure.test :as t]))

(def ^{:private true} s1   '(0     10    20))
(def ^{:private true} e1   '(   5     15    25))
(def ^{:private true} dom1 [0  5  10 15 20 25])
(def ^{:private true} cov1 [1  0   1  0  1  0])


(def ^{:private true} s0 (empty s1))
(def ^{:private true} e0 (empty s1))

;; -----
;; g1bp9
;; Test the algorithm behavior

(t/deftest covify-g1bp9-1
  (let [t (sut/covify s1 e1)]
    (t/testing (t/is (= [dom1 cov1] t)))))


;; -----
;; 53cli
;; Should we proceed with start or with end?

(t/deftest pick-start?-53cli-1
  (t/testing (t/is (= true (sut/pick-start? s1 e1)))))

(t/deftest pick-start?-53cli-2
  (t/testing (t/is (= false (sut/pick-start? (drop 1 s1) e1)))))


;; -----
;; c9dpq
;; If start or end are empty return the domain and cov data

(t/deftest covify-c9dpq-1
  (let [t (sut/covify s0 e0)]
    (t/testing (t/is (= [[] []] t)))))

(t/deftest covify-c9dpq-3
  (let [t (sut/covify s1 e0)]
    (t/testing (t/is (= [[] []] t)))))

(t/deftest covify-c9dpq-4
  (let [t (sut/covify s0 e1)]
    (t/testing (t/is (= [[] []] t)))))
