(ns mathive-thoughts.components
  (:import (java.time LocalDateTime)
           (java.time.format DateTimeFormatter)
           (java.util Locale)))

(def prose "prose dark:prose-invert prose-sm sm:prose-lg")

(defn document [{:keys [title]} & content]
  [:html.dark:bg-zinc-900
   [:head
    (when title [:title title])
    [:link {:rel "preconnect" :href "https://fonts.googleapis.com"}]
    [:link {:rel "preconnect" :href "https://fonts.gstatic.com" :crossorigin true}]
    [:link {:rel "stylesheet"
            :href "https://fonts.googleapis.com/css2?family=Fontdiner+Swanky&display=swap"}]]
   [:body.min-h-screen.bg-texture-light.dark:bg-texture-dark.bg-sm
    content]])

(defn header [logo]
  [:header.w-full.max-w-none.px-4.py-12
   {:class (str prose " bg-zinc-300/40 dark:bg-zinc-950/40")}
   [:nav.container.max-w-prose.flex.gap-4.items-center
    [:a.no-underline.text-current.mx-auto
     {:href "/"}
     [:h2.m-0.grid.grid-flow-col.gap-4.items-center.font-swanky
      "Mathive"
      [:figure.size-20.not-prose.rounded-2xl.overflow-hidden
       [:img {:src logo :alt "Logo"}]]
      "Thoughts"]]]])

(defn footer []
  [:footer.container.flex.gap-4.py-8.px-4
   {:class prose}
   "Mathias Iversen"
   [:a.text-current.no-underline.font-normal.hover:underline
    {:href "https://github.com/TheBoosja"}
    "Github"]
   [:span.grow]
   [:span "2024"]])

(defn article [& content]
  [:article.prose-zinc.container
   {:class prose}
   content])

(defn layout [{:keys [title logo]} & content]
  (document
   {:title title}
   (header logo)
   [:main.py-16.px-4
    (article content)]
   (footer)))

(defn teaser [{:keys [title logo]} & body]
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
