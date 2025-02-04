(ns mathive-thoughts.pages
  (:require [datomic-type-extensions.api :as d]
            [powerpack.markdown :as md]
            [mathive-thoughts.components :as comp]))

(defn get-blog-posts [db]
  (->>
   (d/q '[:find [?e ...]
          :where
          [?e :blog-post/author]]
        db)
   (map #(d/entity db %))
   (sort-by :blog-post/published #(compare %2 %1))))

(def title "Mathive Thoughts")
(def logo "/images/too-massive.webp")

(defn render-frontpage [context page]
  (comp/document
   {:title title}
   [:main.pb-16.pt-4.px-4
    (comp/article
     (comp/teaser
      {:title title :logo logo}
      (md/render-html (:page/body page)))
     [:ul.list-none.!ps-0
      (for [post (get-blog-posts (:app/db context))]
        (comp/post-item post))])]
   (comp/footer)))

(defn render-article [_context page]
  (comp/layout {:logo logo}
               (md/render-html (:page/body page))))

(defn render-blog-post [context page]
  (render-article context page))

(defn render-view-transition-page [_context page]
  (comp/layout {:logo logo
                :class "view-transition-page"}
               (list [:figure.grid.grid-rows-1.items-center.justify-items-center.h-32.overflow-hidden.m-auto.rounded-md
                      {:class "w-10/12"}
                      [:img.w-full.grayscale.blur-sm
                       {:src logo
                        :alt "Mathive Thoughts Logo"}]]
                     (md/render-html (:page/body page)))))

(defn render-page [context page]
  (case (:page/kind page)
    :page.kind/frontpage (render-frontpage context page)
    :page.kind/blog-post (render-blog-post context page)
    :page.kind/view-transition (render-view-transition-page context page)
    :page.kind/article (render-article context page)))

(comment

  '("They're absolutely gigantic!"
    "Seriously, they're huge!"
    "Much wow, very thought"
    "AAAAAAAHHhhhhhh!!!")


  :rfc)
