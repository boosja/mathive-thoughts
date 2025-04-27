(ns mathive-thoughts.components
  (:require [mathive-thoughts.const :as const])
  (:import (java.time LocalDateTime)
           (java.time.format DateTimeFormatter)
           (java.util Locale)))

(defn document [{:keys [title class]} & content]
  [:html.bg-amber-50.dark:bg-zinc-900
   {:class class}
   [:head
    (when title [:title title])
    [:link {:href "/atom.xml"
            :rel "alternate"
            :title "Mathive Thoughts"
            :type "application/atom+xml"}]
    [:link {:rel "preconnect" :href "https://fonts.googleapis.com"}]
    [:link {:rel "preconnect" :href "https://fonts.gstatic.com" :crossorigin true}]
    [:link {:rel "stylesheet"
            :href "https://fonts.googleapis.com/css2?family=Fontdiner+Swanky&display=swap"}]]
   [:body.min-h-screen.bg-texture-light.dark:bg-texture-dark.bg-sm
    content]])

(defn header [logo]
  [:header.w-full.max-w-none.px-4.py-12
   {:class (str const/prose " bg-amber-200/30 dark:bg-zinc-950/40")}
   [:nav.container.max-w-prose.flex.gap-4.items-center
    [:a.no-underline.text-current.mx-auto
     {:href "/"}
     [:h2.!m-0.grid.grid-flow-col.gap-4.items-center.font-swanky
      "Mathive"
      [:img.logo-transition.size-20.not-prose.rounded-2xl.overflow-hidden
       {:src logo
        :alt "Logo"}]
      "Thoughts"]]]])

(defn footer []
  [:footer.container.flex.gap-4.py-8.px-4
   {:class const/prose}
   "Mathias Iversen"
   [:a.text-current.no-underline.font-normal.hover:underline
    {:href "https://github.com/boosja"}
    "Github"]
   [:span.grow]
   [:span "2024-"]])

(defn article [& content]
  [:article.prose-zinc.container
   {:class const/prose}
   content])

(defn ^{:indent 1} layout [{:keys [title logo class]} & content]
  (document
   {:title title
    :class class}
   (header logo)
   [:main.py-16.px-4
    (article content)]
   (footer)))

(def divider
  [:div.my-4.sm:my-6.h-1.bg-gradient-to-r.from-amber-300.to-amber-400.dark:from-amber-300.dark:to-amber-600])

(def divider-2
  [:div.my-4.sm:my-6.h-1.bg-gradient-to-l.from-amber-300.to-amber-400.dark:from-amber-300.dark:to-amber-600])

(defn teaser [{:keys [title logo]} & body]
  [:header.container
   {:class const/prose}
   [:img.logo-transition.rounded-2xl.not-prose.mx-auto.border-2.border-amber-300.p-2
    {:class "w-3/4"
     :src logo
     :alt title}]
   divider
   [:section.px-2.sm:px-6.my-2.sm:my-6.font-bold
    [:h4 body]]
   divider-2])

#_(defn teaser [{:keys [title logo]} & body]
    [:header.container
     {:class prose}
     [:img.rounded-t-2xl.not-prose
      {:src logo
       :alt title}]
     [:section.rounded-b-2xl.px-6.sm:px-8.py-1.bg-gradient-to-b.from-amber-200.to-amber-400.dark:from-amber-300.dark:to-amber-600.font-bold.text-black
      body]])

(def no (Locale/forLanguageTag "no"))

(defn ymd [^LocalDateTime ldt]
  (.format ldt (DateTimeFormatter/ofPattern "d. MMMM y" no)))

(defn post-item [post]
  [:li.group.my-8.first:mt-0
   [:a.block.no-underline.text-current.group-hover:cursor-pointer.m-0
    {:href (:page/uri post)}
    [:h3.m-0.group-hover:underline (:page/title post)]
    [:small.italic (ymd (:blog-post/published post))]
    [:div.line-clamp-2 (:blog-post/desc post)]]])
