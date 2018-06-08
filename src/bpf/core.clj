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
              "dd-MMM-yy HH:mm:ss") s))]
    (/ ms 1000)))

(defn usable-epoch [fname col]
  (sort
   (map to-epoch
        (filter
         (complement clojure.string/blank?)
         (tsv/nth-column fname col)))))

(defn requestnm  [bj-file-name] (tsv/nth-column bj-file-name 2))
(defn startdtm   [bj-file-name] (usable-epoch   bj-file-name 18))
(defn enddtm     [bj-file-name] (usable-epoch   bj-file-name 16))
(defn launchdtm  [bj-file-name] (usable-epoch   bj-file-name 4))

(defn- to-csv
  [infile outfile]
  (let [waiting   (cov/covify (launchdtm infile) (startdtm infile))
        inproc    (cov/covify (startdtm infile)  (enddtm infile))
        [dom cnt] inproc ;; waiting
        tovec     (map vector dom cnt)
        csv       (string/join "\n" (map (fn [x] (string/join "," x)) tovec))]
    (println (str "bpf generate " outfile))
    ;;(println csv)
    (with-open
      [w (clojure.java.io/writer
          outfile
          :append false)]
           (.write w csv))))

(defn -main
  [& args]
  (clojure.pprint/pprint args)
  (cond
    ;; first argument is a directory obtained form the tar file
    (= 2 (count args) ) (to-csv (first args) (second args))

    :else              (println "please specify input directory and output file"))

  ;(dump-to-csv inproc DATA_DIR_HERE BJ_FILE_NAME_HERE)
  )
