(ns bpf.dat-test
  (:require [bpf.dat :as sut]
            [clojure.test :as t]))

(def ^{:private true} bj-file
  (clojure.java.io/file sut/bj-file-name))

(t/deftest bj-file-exist
  (t/testing (t/is (.exists bj-file))))

(t/deftest bj-file-length
  (t/testing (t/is (< 5 (.length bj-file)))))

(t/deftest candidates-length
  (clojure.pprint/pprint sut/candidates)
  (t/testing (t/is (= 4 (count sut/candidates)))))
