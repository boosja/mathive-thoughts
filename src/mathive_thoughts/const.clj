(ns mathive-thoughts.const)

(def title "Mathive Thoughts")
(def logo "/images/too-massive.webp")
(def prose "prose dark:prose-invert prose-sm sm:prose-base")

(defn html-title* [t]
  (if t
    (str t " | " title)
    title))

(defn html-title [page]
  (html-title* (or (:page/title page)
                   (:open-graph/title page))))
