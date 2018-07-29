(ns shires-next-top-model.exercises
  (:gen-class))

;; 1
(str 1 2 3)
(vector 1 2 3)
(list 1 2 3)
(hash-map :key1 1 :key2 2 :key3 3)
(hash-set 1 1 2 2 3)

;; 2
(defn plus100 [x] (+ x 100))
(def anonPlus100 #(+ % 100)) ;; anonymous syntax for above, but named with def

;; 3
(defn dec-maker [x] (fn [y] (- y x)))

;; 4
(defn mapset [func, seq] (set (map func seq)))

;; 5
(def radial-symmetries
  "A list of prefixes for each alien radial symmetry"
  `("left-", "right-", "bottom-left-", "bottom-right-", "top-"))

(defn matching-part
  [part, symmetry]
  {:name (clojure.string/replace (:name part) #"^left-" symmetry)
   :size (:size part)})

(defn matching-alien-parts
  [part]
  (map #(matching-part part %) radial-symmetries))

(defn better-symmetrise-alien-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set (conj (matching-alien-parts part) part))))
          [] asym-body-parts))

;; 6
(defn matching-unknown-symmetry-parts
  [part, symmetry-count]
  (map
    #(matching-part part %)
    (map #(str % "-") (range symmetry-count))))

(defn symmetrise-body-parts-for-unknown-symmetry
  [asym-body-parts, symmetry-count]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set (matching-unknown-symmetry-parts part symmetry-count))))
          [] asym-body-parts))
