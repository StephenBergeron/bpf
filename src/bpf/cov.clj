(ns bpf.cov)

;; assume both list are sorted
(defn pick-start? [s e]
  (let [min-start (first s)
        min-end   (first e)]
    (< min-start min-end)))

(defn covify
  [si ei]
  (loop [dom []
         cnt []
         s   si
         e   ei]
    (if (empty? s)
      [dom cnt]
      (let [c (pick-start? s e)
            lastcnt (cond (empty? cnt) 0 :else (last cnt))
            newcnt  (cond c (+ 1 lastcnt) :else (- 1 lastcnt))
            newdom  (cond c (first s) :else (first e))
            s-new   (cond c (drop 1 s) :else s)
            e-new   (cond c e :else (drop 1 e))]
        (recur (conj dom newdom) (conj cnt newcnt) s-new e-new)
        )
      )))
