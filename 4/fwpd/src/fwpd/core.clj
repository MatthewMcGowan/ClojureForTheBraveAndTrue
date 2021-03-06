(ns fwpd.core
  (:gen-class))

(def filename "resources/suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (glitter-filter 3 (mapify (parse (slurp filename))))))


;; 1
(map :name (glitter-filter 3 (mapify (parse (slurp filename)))))

;; 2
(defn append [suspect-list suspect] (conj suspect-list suspect))

;; 3
(def example-validations {:name identity :glitter-index identity})
(def example-good-record {:name "name" :glitter-index 5})
(def example-bad-record {:name "name" :something-else "hello"})
(defn validate
  [validations record]
  (every? identity
         (map
           #((% validations) (% record))
           (keys validations))))

;; 4
(defn csvify
  [records]
  (clojure.string/join "\n"
       (map
         #(clojure.string/join "," (map str (vals %)))
         records)))
