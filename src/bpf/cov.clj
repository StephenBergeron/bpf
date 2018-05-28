(ns bpf.cov)

;; assume both list are sorted
(defn pick-start? [s e]
  (cond
    (empty? e) true
    (empty? s) false
    :else (< (first s) (first e))))

(defn covify
  [si ei]
  (loop [dom []
         cnt []
         s   si
         e   ei]
    (if (and (empty? s) (empty? e))
      [dom cnt]
      (let [c  (pick-start? s e)
            lastcnt (cond (empty? cnt) 0 :else (last cnt))
            newcnt  (cond c (+ lastcnt 1) :else (- lastcnt 1))
            newdom  (cond c (first s) :else (first e))
            s-new   (cond c (drop 1 s) :else s)
            e-new   (cond c e :else (drop 1 e))]
        (recur
         (conj (conj dom newdom) newdom)
         (conj (conj cnt lastcnt) newcnt)
         s-new e-new)
        )
      )))
