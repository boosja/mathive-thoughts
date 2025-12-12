(ns mathive-thoughts.core
  (:require [mathive-thoughts.const :as const]
            [mathive-thoughts.ingest :as ingest]
            [mathive-thoughts.pages :as pages]
            [powerpack.highlight :as highlight]))

(defn create-app []
  (-> {:site/title const/title
       :site/default-locale :nb

       :powerpack/render-page #'pages/render-page
       :powerpack/create-ingest-tx #'ingest/create-tx

       :optimus/bundles {"app.css"
                         {:public-dir "public"
                          :paths ["/styles.css"]}}

       :optimus/assets [{:public-dir "public"
                         :paths [#"/*\.(png|ico)"
                                 #"images/.*\..*"
                                 #"videos/.*\..*"]}]}
      highlight/install))
