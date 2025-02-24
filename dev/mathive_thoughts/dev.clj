(ns mathive-thoughts.dev
  (:require [mathive-thoughts.core :as blog]
            [powerpack.dev :as dev]))

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
