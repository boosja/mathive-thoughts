(ns mathive-thoughts.ingest)

(defn get-page-kind [filename]
  (cond
    (re-find #"^blog/" filename)
    :page.kind/blog-post

    (re-find #"^index\.md" filename)
    :page.kind/frontpage

    (re-find #"^view-transition-.\.md" filename)
    :page.kind/view-transition

    (re-find #"\.md$" filename)
    :page.kind/article))

(defn create-tx [filename txes]
  (let [kind (get-page-kind filename)]
    (for [tx txes]
      (cond-> tx
        (and (:page/uri tx) kind)
        (assoc :page/kind kind)))))

(comment

  (re-find #"^view-transition-.\.md" "view-transition-1.md")

  :rfc)
