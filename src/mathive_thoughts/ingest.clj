(ns mathive-thoughts.ingest)

(defn ingest-blog-posts [blog-post]
  (-> blog-post
      (assoc :page/kind :page.kind/blog-post)))

(defn ingest-rest [page]
  (if-not (:page/kind page)
    (assoc page :page/kind :page.kind/article)
    page))

(defn create-tx [filename datas]
  (cond->> datas
    (re-find #"^blog-posts/" filename)
    (map ingest-blog-posts)

    (re-find #"\.md$" filename)
    (map ingest-rest)))

(comment

  (re-find #"^view-transition-.\.md" "view-transition-1.md")

  :rfc)
