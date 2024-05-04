(ns mathive-thoughts.core
  (:require [mathive-thoughts.ingest :as ingest]
            [mathive-thoughts.pages :as pages]))

(def config
  {:site/title pages/title
   :powerpack/render-page #'pages/render-page
   :powerpack/create-ingest-tx #'ingest/create-tx

   :optimus/bundles {"app.css"
                     {:public-dir "public"
                      :paths ["/styles.css"]}}

   :optimus/assets [{:public-dir "public"
                     :paths [#".*\.svg"
                             #".*\.png"
                             #".*\.webp"]}]})
