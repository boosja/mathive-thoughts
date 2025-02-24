(ns mathive-thoughts.blog-posts
  (:require [datomic-type-extensions.api :as d]))

(defn get-blog-posts [db]
  (->>
   (d/q '[:find [?e ...]
          :where
          [?e :blog-post/author]]
        db)
   (map #(d/entity db %))
   (sort-by :blog-post/published #(compare %2 %1))))
