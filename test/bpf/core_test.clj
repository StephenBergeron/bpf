(ns bpf.core-test
  (:require [bpf.core :as sut]
            [bpf.dat :as dat]
            [clojure.test :as t]))

(t/deftest startdtm-count
  (t/testing (t/is (< 0 (count sut/startdtm)))))

(t/deftest startdtm-type
  (t/testing
      (t/is (= clojure.lang.ArraySeq (type sut/startdtm)))
    (t/is (= java.lang.Long (type (first sut/startdtm))))
    ))

(t/deftest bj-has-tsv-content
  (t/testing (t/is (< 0 (count (sut/parse-tsv dat/bj-file-name))))))

;; (deftest content-valid
;;   (testing (is (= "hello" enddtm ))))

(t/deftest generic-can-read-tab-delimiter
  (t/testing
    (t/is (= [["First", "Second"]]
           (clojure-csv.core/parse-csv "First\tSecond" :delimiter \tab)))))
