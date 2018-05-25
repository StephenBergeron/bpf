(ns bpf.core
  (:gen-class)
  (:require [clojure-csv.core :as clojure-csv.core]
            [clojure.pprint]
            [bpf.cov :as cov]))

(def bj-file-name
  (str "/home/stn/cache/wfm87/" "53568.dat"))

(defn tsv
  [fname]
  (clojure-csv.core/parse-csv (slurp fname) :delimiter \tab))

(defn nth-column
  [fname column]
  (let [tabl (tsv fname)]
    (remove nil? (map (fn [x] (nth x column nil)) tabl))))

(defn to-epoch
  [s]
  (.getTime (.parse (java.text.SimpleDateFormat. "yyyy-MM-dd HH:mm:ss") s)))

(def requestnm (nth-column bj-file-name 2))
(def startdtm (sort (map to-epoch (nth-column bj-file-name 17))))
(def enddtm (sort (map to-epoch (nth-column bj-file-name 15))))
(def launchdtm (sort (map to-epoch (nth-column bj-file-name 3))))

(def waiting (cov/covify launchdtm startdtm))
(def inproc  (cov/covify startdtm  enddtm))

(defn -main
  [& args]
  (let [[dom cnt] (cov/covify startdtm enddtm)]
    (clojure.pprint/pprint (map vector dom cnt))))
