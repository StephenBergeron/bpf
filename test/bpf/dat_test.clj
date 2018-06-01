(ns bpf.dat-test
  (:require [bpf.dat :as sut]
            [clojure.test :as t]
            [bpf.tsv :as tsv]))

(def ^{:private true} data-dir
  (str (System/getenv "CACHEDIR") "/" (System/getenv "simulation")))

(def ^{:private true} bj-file
  (clojure.java.io/file (sut/bj-file-name data-dir)))

(t/deftest bj-file-exist
  (t/testing (t/is (.exists bj-file))))

(t/deftest bj-file-length
  (t/testing (t/is (< 5 (.length bj-file)))))

(t/deftest locate-dat-files-length
  (println "locate-dat-files:")
  (clojure.pprint/pprint (sut/locate-dat-files data-dir))
  (t/testing (t/is (= 4 (count (sut/locate-dat-files data-dir))))))

(t/deftest bj-has-tsv-content
  (t/testing (t/is (< 0 (count (tsv/parse-tsv (sut/bj-file-name data-dir)))))))
