(ns bpf.core
  (:gen-class)
  (:require [clojure-csv.core :as clojure-csv.core]))

(defn nth-colum
  [tabl column]
  (remove nil? (map (fn [x] (nth x column nil)) tabl)))

(def bj-file-name
  (str "/home/stn/cache/wfm87/" "53568.dat"))

(def bj
  (clojure-csv.core/parse-csv (slurp bj-file-name) :delimiter \tab))

(def startdtm (nth-colum bj 17))
(def enddtm (nth-colum bj 15))
(def launchdtm (nth-colum bj 3))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
