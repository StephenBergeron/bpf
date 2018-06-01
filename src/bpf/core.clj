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

(defn waiting [bj-file-name] (cov/covify (launchdtm bj-file-name) (startdtm bj-file-name)))
(defn inproc  [bj-file-name] (cov/covify (startdtm bj-file-name)  (enddtm bj-file-name)))

(defn- dump-to-csv
  [cov data-dir fname]
  (let [[dom cnt] cov
        tovec     (map vector dom cnt)
        csv       (string/join "\n" (map (fn [x] (string/join "," x)) tovec))]
    (println (str "bpf generate " fname))
    (println csv)
    (with-open
      [w (clojure.java.io/writer
          (str data-dir "/" fname)
          :append false)]
           (.write w csv))))

(defn -main
  [& args]
  (println "FIXME :/")
  ;(println (str "Processing " DATA_DIR_HERE BJ_FILE_NAME_HERE))
  ;(dump-to-csv inproc DATA_DIR_HERE BJ_FILE_NAME_HERE)
  )
