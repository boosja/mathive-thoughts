(ns mathive-thoughts.frontpage
  (:require [mathive-thoughts.blog-posts :as blog-posts]
            [mathive-thoughts.components :as comp]
            [mathive-thoughts.const :as const]
            [mathive-thoughts.diminutives :as diminutives]
            [powerpack.markdown :as md]))

#_"The constant state of mind caused by the ecstacy of learning Clojure"

(def teaser-txt
  "Bloggen der hjerner kommer for å dø... fra innsikten gitt av kunnskapens
overdådige frukt. Neida 😅, dette er stedet hvor jeg deler mine erfaringer og
tanker om temaer jeg interesserer meg for og lærer om.

\"Mathive\" i Mathive Thoughts er navnet mitt mast sammen (*Math*ias *Ive*rsen)
og høres ut som noen lesper det engelske ordet \"massive\". Passende, siden jeg
fordyper meg i Clojure, en dialekt av Lisp 😜. Ironisk, for jeg vet ikke et
kvidder.")

(defn render [context _page]
  (let [diminutives (take 2 (diminutives/get-diminutives (:app/db context)))
        blog-posts (blog-posts/get-blog-posts (:app/db context))]
   (comp/document
    {:title const/title}
    [:main.pb-16.pt-4.px-4
     (comp/article
      (comp/teaser
       {:title const/title :logo const/logo}
       (md/render-html teaser-txt))
      [:div.text-right.group
       (comp/anchor {:href "/diminutives/"} "Se alle")]
      [:ul.list-none.!mt-0.!ps-0.grid.grid-cols-2.gap-2
       (for [dim diminutives]
         (comp/diminutive dim))]
      [:ul.list-none.!ps-0
       (for [post blog-posts]
         (comp/post-item post))])]
    (comp/footer))))
