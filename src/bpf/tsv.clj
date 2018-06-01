(ns bpf.tsv
  (:require [clojure-csv.core :as clojure-csv.core]))


                                        ; fname - full path of the tsv file
(defn parse-tsv
  [fname]
  (clojure-csv.core/parse-csv (slurp fname) :delimiter \tab))


                                        ; fname  - full path of the tsv file
                                        ; column - column number
(defn nth-column
  [fname column]
  (let [tabl    (parse-tsv fname)
        raw-col (map (fn [x] (nth x column nil)) tabl)
        processed (remove (fn [x] (= "\\N" x)) raw-col)]
    (remove nil? processed)))
