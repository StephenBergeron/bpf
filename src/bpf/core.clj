(ns bpf.core
  (:gen-class)
  (:require [clojure.string :as string]
            [clojure.pprint]
            [bpf.tsv :as tsv]
            [bpf.cov :as cov]
            [bpf.dat :as dat]))

(defn to-epoch
  [s]
  (let [ms (.getTime
            (.parse
             (java.text.SimpleDateFormat.
              "yyyy-MM-dd HH:mm:ss") s))]
    (/ ms 1000)))

(defn requestnm  [bj-file-name] (tsv/nth-column bj-file-name 2))
(defn startdtm   [bj-file-name] (sort (map to-epoch (tsv/nth-column bj-file-name 17))))
(defn enddtm     [bj-file-name] (sort (map to-epoch (tsv/nth-column bj-file-name 15))))
(defn launchdtm  [bj-file-name] (sort (map to-epoch (tsv/nth-column bj-file-name 3))))

(defn- to-csv
  [infile outfile]
  (let [waiting   (cov/covify (launchdtm infile) (startdtm infile))
        inproc    (cov/covify (startdtm infile)  (enddtm infile))
        [dom cnt] inproc ;; waiting
        tovec     (map vector dom cnt)
        csv       (string/join "\n" (map (fn [x] (string/join "," x)) tovec))]
    (println (str "bpf generate " outfile))
    (println csv)
    (with-open
      [w (clojure.java.io/writer
          outfile
          :append false)]
           (.write w csv))))

(defn -main
  [& args]
  (clojure.pprint/pprint args)
  (cond

    (= 2 (count args) ) (to-csv (first args) (second args))

    :else              (println "please specify input and output files"))

  ;(dump-to-csv inproc DATA_DIR_HERE BJ_FILE_NAME_HERE)
  )
