(ns bpf.core
  (:gen-class)
  (:require [clojure-csv.core :as clojure-csv.core]
            [clojure.string :as string]
            [clojure.pprint]
            [bpf.cov :as cov]
            [bpf.dat :as dat]))

(defn parse-tsv
  [fname]
  (clojure-csv.core/parse-csv (slurp fname) :delimiter \tab))

(defn nth-column
  [fname column]
  (let [tabl    (parse-tsv fname)
        raw-col (map (fn [x] (nth x column nil)) tabl)
        processed (remove (fn [x] (= "\\N" x)) raw-col)]
    (remove nil? processed)))

(defn to-epoch
  [s]
  (let [ms (.getTime
            (.parse
             (java.text.SimpleDateFormat.
              "yyyy-MM-dd HH:mm:ss") s))]
    (/ ms 1000)))

(def requestnm (nth-column dat/bj-file-name 2))
(def startdtm (sort (map to-epoch (nth-column dat/bj-file-name 17))))
(def enddtm (sort (map to-epoch (nth-column dat/bj-file-name 15))))
(def launchdtm (sort (map to-epoch (nth-column dat/bj-file-name 3))))

(def waiting (cov/covify launchdtm startdtm))
(def inproc  (cov/covify startdtm  enddtm))

(defn- dump-to-csv
  [cov fname]
  (let [[dom cnt] cov
        tovec     (map vector dom cnt)
        csv       (string/join "\n" (map (fn [x] (string/join "," x)) tovec))]
    (println (str "bpf generate " fname))
    (println csv)
    (with-open
      [w (clojure.java.io/writer
          (str dat/data-dir "/" fname)
          :append false)]
           (.write w csv))))

(defn -main
  [& args]
  (println (str "Processing " dat/bj-file-name))
  (dump-to-csv inproc "bj_proc.csv"))
