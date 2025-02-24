(ns mathive-thoughts.frontpage
  (:require [mathive-thoughts.blog-posts :as blog-posts]
            [mathive-thoughts.components :as comp]
            [mathive-thoughts.const :as const]
            [powerpack.markdown :as md]))

(def teaser-txt
  "Bloggen der hjerner kommer for å dø... fra innsikten gitt av kunnskapens
overdådige frukt. Neida 😅, dette er stedet hvor jeg deler mine erfaringer og
tanker om temaer jeg interesserer meg for og lærer om.

\"Mathive\" i Mathive Thoughts er navnet mitt mast sammen (*Math*ias *Ive*rsen)
og høres ut som noen lesper det engelske ordet \"massive\". Passende, siden jeg
fordyper meg i Clojure, en dialekt av Lisp 😜. Ironisk, for jeg vet ikke et
kvidder.")

(defn render [context _page]
  (comp/document
   {:title const/title}
   [:main.pb-16.pt-4.px-4
    (comp/article
     (comp/teaser
      {:title const/title :logo const/logo}
      (md/render-html teaser-txt))
     [:ul.list-none.!ps-0
      (for [post (blog-posts/get-blog-posts (:app/db context))]
        (comp/post-item post))])]
   (comp/footer)))
