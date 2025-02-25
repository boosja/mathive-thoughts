(ns mathive-thoughts.pages
  (:require [mathive-thoughts.components :as comp]
            [mathive-thoughts.const :as const]
            [mathive-thoughts.frontpage :as frontpage]
            [mathive-thoughts.view-transition-page :as view-transition-page]
            [powerpack.markdown :as md]))

(defn render-article [_context page]
  (comp/layout {:logo const/logo}
    (list
     (when-let [title (:page/title page)]
       [:h1 title])
     (md/render-html (:page/body page)))))

(defn render-blog-post [context page]
  (render-article context page))

(defn render-page [context page]
  (case (:page/kind page)
    :page.kind/frontpage (frontpage/render context page)
    :page.kind/blog-post (render-blog-post context page)
    :page.kind/view-transition-1 (view-transition-page/render-page-1 context page)
    :page.kind/view-transition-2 (view-transition-page/render-page-2 context page)
    :page.kind/article (render-article context page)))

(comment

  '("They're absolutely gigantic!"
    "Seriously, they're huge!"
    "Much wow, very thought"
    "AAAAAAAHHhhhhhh!!!")


  :rfc)
