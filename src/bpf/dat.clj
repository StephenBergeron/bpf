(ns ^{:author "Bergeron, Stephen"
      :doc "Allow to locate dat file"}
    bpf.dat
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

                                        ; directory to use
(def data-dir
  (str (System/getenv "CACHEDIR") "/" (System/getenv "simulation")))

(def dat-files
  (sort (filter
         (fn [x] (string/ends-with? x ".dat"))
         (.list (io/file data-dir)))))

(def bj-file-name
  (str data-dir "/" (first dat-files)))
