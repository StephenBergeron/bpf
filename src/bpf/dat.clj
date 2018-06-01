(ns ^{:author "Bergeron, Stephen"
      :doc "Allow to locate dat file"}
    bpf.dat
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn dat-files
  [data-dir]
  (sort (filter
         (fn [x] (string/ends-with? x ".dat"))
         (.list (io/file data-dir)))))

(defn bj-file-name
  [data-dir]
  (str data-dir "/" (first (dat-files data-dir))))
