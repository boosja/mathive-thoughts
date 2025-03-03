(ns mathive-thoughts.rss
  (:require [clojure.data.xml :as xml]
            [datomic-type-extensions.api :as d]
            [hiccup.core :refer [html]]
            [mathive-thoughts.blog-posts :as blog-posts]
            [powerpack.markdown :as md])
  (:import [java.time ZoneId]
           [java.time.format DateTimeFormatter]))

(defn url [post]
  (str "https://mathivethoughts.no" (:page/uri post)))

(defn time-str [ldt]
  (-> ldt
      (.atZone (ZoneId/of "Europe/Oslo"))
      .toOffsetDateTime
      (.format DateTimeFormatter/ISO_OFFSET_DATE_TIME)))

(defn entry [post]
  [:entry
   [:title (:page/title post)]
   [:updated (time-str (:blog-post/published post))]
   [:author [:name (-> post :blog-post/author :person/full-name)]]
   [:link {:href (url post)}]
   [:id (str "tag:mathivethoughts.no,2024:post-"
             (.toLocalDate (:blog-post/published post)))]
   [:content {:type "html"}
    (html
        [:div
         [:div (md/render-html (:blog-post/desc post))]
         [:p [:a {:href (url post)} "Les artikkelen"]]])]])

(defn atom-xml [blog-posts]
  (xml/emit-str
   (xml/sexp-as-element
    [:feed {:xmlns "http://www.w3.org/2005/Atom"
            :xmlns:media "http://search.yahoo.com/mrss/"}
     [:id "tag:mathivethoughts.no,2024:feed"]
     [:updated (time-str (:blog-post/published (first blog-posts)))]
     [:title {:type "text"} "Mathive Thoughts"]
     [:link {:rel "self" :href "https://mathivethoughts.no/atom.xml"}]
     (map entry blog-posts)])))

(defn blog-post-feed [page]
  {:status 200
   :headers {"Content-Type" "application/atom+xml"}
   :body (-> (d/entity-db page)
             blog-posts/get-blog-posts
             atom-xml)})

(comment

  (def system integrant.repl.state/system)
  (def db (d/db (:datomic/conn (:powerpack/app system))))

  (def posts (blog-posts/get-blog-posts db))

  (def post (first posts))

  (entry post)

  (atom-xml posts)

  )
