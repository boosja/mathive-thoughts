(ns mathive-thoughts.dev
  (:require [clojure.string :as str]
            [mathive-thoughts.core :as blog]
            [mathive-thoughts.diminutives :as diminutives]
            [powerpack.dev :as dev])
  (:import [java.time LocalDateTime]))

(defmethod dev/configure! :default []
  (blog/create-app))

(comment

  (dev/start)
  (dev/stop)
  (dev/reset)

  (set! *print-namespace-maps* false)

  (do
    (def app (dev/get-app))
    (require '[datomic.api :as d])
    (def db (d/db (:datomic/conn app))))

  (->> (d/entity db [:page/kind])
       :blog-post/tags
       vec)

  (->> (d/q '[:find [?e ...]
              :where
              [?e :page/kind]]
            db)
       (map #(d/entity db %))
       (map #(into {} %)))

  :rfc)

;; =================================================
;;     Diminutive thoughts
;; =================================================

(def diminutives-path "content/diminutives.edn")

(defn with-newlines [v]
  (-> (binding [*print-length* nil
                *print-level* nil]
        (pr-str v))
      (str/replace #"}\s" "}\n ")
      (str/replace #"\",\s" "\"\n  ")))

(defn add-diminutive [text]
  (let [diminutive (read-string (slurp diminutives-path))]
    (->> {:diminutive/text text
          :diminutive/date (LocalDateTime/now)}
         (conj diminutive)
         (with-newlines)
         (spit diminutives-path))))

(comment
  ;; Add shortie
  (add-diminutive
   "Sjekk ut [based/](based/), da vel!"
   )

  (diminutives/get-diminutives db)

  )
