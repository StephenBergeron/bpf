(ns bpf.core-test
  (:require [bpf.core :as sut]
            [bpf.dat :as dat]
            [clojure.test :as t]))

(def ^{:private true} data-dir
  (str (System/getenv "CACHEDIR") "/" (System/getenv "simulation")))

(def ^{:private true} bj-file
  (clojure.java.io/file (dat/bj-file-name data-dir)))


(def dtm-like (sut/startdtm bj-file))


(t/deftest startdtm-count
  (t/testing (t/is (< 0 (count dtm-like)))))

(t/deftest startdtm-type
  (t/testing
      (t/is (= clojure.lang.ArraySeq (type dtm-like)))
    (t/is (= java.lang.Long (type (first dtm-like))))
    ))

(t/deftest bj-has-tsv-content
  (t/testing (t/is (< 0 (count (sut/parse-tsv (dat/bj-file-name data-dir)))))))

;; (deftest content-valid
;;   (testing (is (= "hello" enddtm ))))

(t/deftest generic-can-read-tab-delimiter
  (t/testing
    (t/is (= [["First", "Second"]]
           (clojure-csv.core/parse-csv "First\tSecond" :delimiter \tab)))))
