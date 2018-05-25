(ns bpf.core
  (:gen-class)
  (:require [clojure-csv.core :as clojure-csv.core]))

(def bj-file-name
  (str "/home/stn/cache/wfm87/" "53568.dat"))

(defn tsv
  [fname]
  (clojure-csv.core/parse-csv (slurp fname) :delimiter \tab))

(defn nth-colum
  [fname column]
  (let [tabl (tsv fname)]
    (remove nil? (map (fn [x] (nth x column nil)) tabl))))

(defn to-epoch
  [s]
  (.getTime (.parse (java.text.SimpleDateFormat. "yyyy-MM-dd HH:mm:ss") s)))

(def requestnm (nth-colum bj-file-name 2))
(def startdtm (map to-epoch (nth-colum bj-file-name 17)))
(def enddtm (map to-epoch (nth-colum bj-file-name 15)))
(def launchdtm (map to-epoch (nth-colum bj-file-name 3)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
