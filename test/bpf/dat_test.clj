(ns bpf.dat-test
  (:require [bpf.dat :as sut]
            [clojure.test :as t]))

(def ^{:private true} data-dir
  (str (System/getenv "CACHEDIR") "/" (System/getenv "simulation")))

(def ^{:private true} bj-file
  (clojure.java.io/file (sut/bj-file-name data-dir)))

(t/deftest bj-file-exist
  (t/testing (t/is (.exists bj-file))))

(t/deftest bj-file-length
  (t/testing (t/is (< 5 (.length bj-file)))))

(t/deftest dat-files-length
  (println "dat-files for bj:")
  (clojure.pprint/pprint (sut/dat-files data-dir))
  (t/testing (t/is (= 4 (count (sut/dat-files data-dir))))))
