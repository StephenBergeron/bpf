(ns ^{:author "Bergeron, Stephen"
      :doc "Allow to locate dat file"}
    bpf.dat)

(def bj-file-name
  (str
   (System/getenv "CACHEDIR")
   "/"
   (System/getenv "simulation")
   "/"
   "78265.dat"))
