(ns mathive-thoughts.based
  (:require [clojure.string :as str]
            [mathive-thoughts.components :as comp]
            [mathive-thoughts.const :as const]))

(defn with-separators [num-str]
  (if (< 4 (count num-str))
    (clojure.string/replace num-str #"(?<=\d)(?=(\d{3})+$)" ".")
    num-str))

(defn based-numbers [bases numbers]
  (-> (fn [v]
        (-> #(with-separators
               (str/upper-case (Integer/toString v %)))
            (map bases)))
      (map numbers)))

(def alphabet #{"0" "1" "2" "3" "4" "5" "6" "7" "8" "9"
                "A" "B" "C" "D" "E" "F" "G" "H" "I" "J"
                "K" "L" "M" "N" "O" "P" "Q" "R" "S" "T"
                "U" "V" "W" "X" "Y" "Z"})
(def the-tens #{"10" "20" "30" "40" "50" "60" "70" "80" "90"})
(def pows-o-10 #{"10" "100" "1000" "10.000" "100.000" "1.000.000"
                 "10.000.000" "100.000.000" "1.000.000.000"})
(def pows-o-2 #{"2" "4" "8" "16" "32" "64" "128" "256" "512" "1024" "2048"
                "4096" "8192" "16384" "32768" "65536" "131072" "262144" "524288"})

(defn style-cell [n i v]
  (-> (into [] (when (and (< n 10) (< i 9))
                 ["bg-white/45 dark:bg-black/35"]))
      (concat (cond
                (and (even? n) (< n 10) (< 8 i)) ["bg-white" "dark:bg-black"
                                                  "text-zinc-300" "dark:text-zinc-600"]
                (and (contains? alphabet v) (< 8 i)) ["text-zinc-300" "dark:text-zinc-600"]
                (< n 10) [] ;; create safezone in upper left corner
                (contains? the-tens v) ["bg-zinc-200" "text-black"
                                        "dark:bg-transparent" "dark:text-white"]
                (contains? pows-o-10 v) ["bg-stone-200" "text-zinc-500"
                                         "dark:bg-zinc-800" "dark:text-current"]
                (contains? pows-o-2 v) ["bg-emerald-100"
                                        "dark:bg-transparent" "dark:text-emerald-600"]))))

(defn render [_context page]
  (comp/document
   {:title (const/html-title page)}
   (comp/header const/logo)

   [:main.py-16.px-4
    (let [bases (range 2 37)
          numbers (range 513)]
      [:table.based.m-auto.font-mono {:class const/prose}
       [:caption.caption-top.p-4
        [:small "Tabell 1.1: Tall i base 2 til 36"]]

       [:thead.sticky.top-0.bg-zinc-100.dark:bg-zinc-800
        [:tr [:th "N / Base"]
         (map #(vector :th %) bases)]]

       [:tbody
        (map-indexed
         (fn [n vs]
           [:tr {:class (when (and (even? n) (< 9 n)) "bg-white dark:bg-black")}
            [:th {:class (when (< n 10) "bg-white/45 dark:bg-black/35")} n]
            (-> (fn [i v]
                  [:td {:class (style-cell n i v)}
                   v])
                (map-indexed vs))])
         (based-numbers bases numbers))]])]

   (comp/footer)))
