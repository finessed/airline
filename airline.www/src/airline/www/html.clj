(ns airline.www.html
  (:require [hiccup.page :as h]
            [hiccup.element :as ele]))

(def css-resources 
  ["/lib/bootstrap/3.2.0/css/bootstrap.min.css" 
   "/css/dusk.css"])

(def ie-js-resources 
  ["/lib/html5shiv/3.7.2/html5shiv.min.js"
   "/lib/respond/1.4.2/respond.min.js" ])

(def common-header
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
   [:meta {:name       "viewport"        :content "width=device-width, initial-scale=1"}]
   [:meta {:name       "description"     :content ""}]
   [:meta {:name       "author"          :content "devstopfix.com"}]
   [:link {:rel "icon" :href "/favicon.ico"}]
   (for [css css-resources]
     (h/include-css css))
   "<!--[if lt IE 9]>"
   (for [js ie-js-resources]
     (h/include-js js))
   "<![endif]-->"])

(defn head [title]
  (conj common-header
    [:title title]))


(defn home-page []
    (h/html5
      (head "Dusk Airlines")
      [:body
       [:div.container
        [:div.row]]]))




