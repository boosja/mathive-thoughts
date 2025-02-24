(ns mathive-thoughts.view-transition-page
  (:require [mathive-thoughts.components :as comp]
            [mathive-thoughts.const :as const]))

(defn ^{:indent 2} render-view-transition-page [_context _page body]
  (comp/layout {:logo const/logo
                :class "view-transition-page"}
               (list [:figure.grid.grid-rows-1.items-center.justify-items-center.h-32.overflow-hidden.m-auto.rounded-md
                      {:class "w-10/12"}
                      [:img.w-full.grayscale.blur-sm
                       {:src const/logo
                        :alt "Mathive Thoughts Logo"}]]
                     body)))

(defn render-page-1 [context page]
  (render-view-transition-page context page
    (list
     [:blockquote
      [:p "\"You're gonna need a bigger transition.\" – Views"]]
     [:p [:a {:href "/view-transition-2/"}
          "Page 2"]]
     [:hr]
     [:p [:a {:href "/blog-posts/view-transition/"}
          "Tilbake til artikkelen"]])))

(defn render-page-2 [context page]
  (render-view-transition-page context page
    (list
     [:blockquote
      [:p "\"I love the smell of transitions in the morning.\" – Transition Now"]]
     [:p [:a {:href "/view-transition-1/"}
          "Page 1"]]
     [:hr]
     [:p [:a {:href "/blog-posts/view-transition/"}
          "Tilbake til artikkelen"]])))
