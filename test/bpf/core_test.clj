(ns bpf.core-test
  (:require [clojure.test :refer :all]
            [bpf.core :refer :all]))

(def ^{:private true} bj-file
  (clojure.java.io/file bj-file-name))

(deftest bj-file-exist
  (testing (is (.exists bj-file))))

(deftest bj-file-length
  (testing (is (< 5 (.length bj-file)))))

(deftest bj-count
  (testing (is (< 0 (count bj)))))

(deftest startdtm-count
  (testing (is (< 0 (count startdtm)))))

;; (deftest content-valid
;;   (testing (is (= "hello" enddtm ))))

(deftest generic-can-read-tab-delimiter
  (testing
    (is (= [["First", "Second"]]
           (clojure-csv.core/parse-csv "First\tSecond" :delimiter \tab)))))
