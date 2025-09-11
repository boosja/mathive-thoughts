(ns mathive-thoughts.ingest
  (:require [mathive-thoughts.const :as const]))

(defn ingest-blog-posts [blog-post]
  (-> blog-post
      (assoc :page/kind :page.kind/blog-post)))

(defn ingest-rest [page]
  (if-not (:page/kind page)
    (assoc page :page/kind :page.kind/article)
    page))

(defn get-open-graph-title [page]
  (const/html-title* (or (:open-graph/title page)
                         (:page/title page))))

(defn get-open-graph-description [page]
  (or (:open-graph/description page)
      (:blog-post/desc page)
      "Der mine overveldende tanker ekspanderes og får brått utløp"))

(defn apply-open-graphs [page]
  (if (:page/uri page)
    (-> page
        (assoc :open-graph/title (get-open-graph-title page))
        (assoc :open-graph/description (get-open-graph-description page))
        (update :open-graph/image #(or % "/images/og-too-massive.jpeg")))
    page))

(defn create-tx [filename datas]
  (cond->> datas
    (re-find #"^blog-posts/" filename)
    (map ingest-blog-posts)

    (re-find #"\.md$" filename)
    (map ingest-rest)

    (re-find #"diminutives.edn" filename)
    identity

    :else
    (map apply-open-graphs)))


(comment

  (re-find #"^view-transition-.\.md" "view-transition-1.md")

  :rfc)
