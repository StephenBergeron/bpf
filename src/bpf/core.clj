(ns bpf.core
  (:gen-class)
  (:require [clojure-csv.core :as clojure-csv.core]
            [clojure.pprint]
            [bpf.cov :as cov]
            [bpf.dat :as dat]))

(defn tsv
  [fname]
  (clojure-csv.core/parse-csv (slurp fname) :delimiter \tab))

(defn nth-column
  [fname column]
  (let [tabl    (tsv fname)
        raw-col (map (fn [x] (nth x column nil)) tabl)
        processed (remove (fn [x] (= "\\N" x)) raw-col)]
    (remove nil? processed)))

(defn to-epoch
  [s]
  (.getTime (.parse (java.text.SimpleDateFormat. "yyyy-MM-dd HH:mm:ss") s)))

(def requestnm (nth-column dat/bj-file-name 2))
(def startdtm (sort (map to-epoch (nth-column dat/bj-file-name 17))))
(def enddtm (sort (map to-epoch (nth-column dat/bj-file-name 15))))
(def launchdtm (sort (map to-epoch (nth-column dat/bj-file-name 3))))

(def waiting (cov/covify launchdtm startdtm))
(def inproc  (cov/covify startdtm  enddtm))

(defn -main
  [& args]
  (println (str "Processing " dat/bj-file-name))
  (let [[dom cnt] (cov/covify startdtm enddtm)]
    (clojure.pprint/pprint (map vector dom cnt))))
