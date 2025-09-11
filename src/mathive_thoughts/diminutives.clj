(ns mathive-thoughts.diminutives
  (:require [datomic-type-extensions.api :as d]
            [mathive-thoughts.components :as comp]
            [mathive-thoughts.const :as const]
            [powerpack.markdown :as md]))

(defn get-diminutives [db]
  (->> (d/q '[:find [?e ...]
              :where [?e :diminutive/text]]
            db)
       (map #(d/entity db %))
       (sort-by :diminutive/date #(compare %2 %1))))

(defn render [context page]
  (let [diminutives (get-diminutives (:app/db context))]
    (comp/layout {:logo const/logo
                  :title (const/html-title page)}
                 [:h1 "Diminutive Tanker"]
                 [:div.grid.gap-4
                  (for [dim diminutives]
                    [:div.border-b.border-zinc-600.pb-2
                     [:small (comp/ymdhm (:diminutive/date dim))]
                     [:div
                      (md/render-html (:diminutive/text dim))]])])))
