(ns frontend.css.links
  (:require
   [clojure.string :refer [starts-with?]]))

(defn link-css [prefix link]
  (if (or (starts-with? link "http")
          (starts-with? link "/"))
    link
    (str prefix link)))

(defn css-component [prefix available component-kw component-theme]
  (let [component-theme (or component-theme false) ;  (get config component-kw) false)
        get-theme (fn [theme]
                    (or (get-in available [component-kw theme]) []))
        links (case component-theme
                false []
                true (get-theme true)
                (concat
                 (get-theme true)
                 (get-theme component-theme)))]
    (into []
          (map (partial link-css prefix) links))))

(defn css-app [prefix {:keys [available current] :as theme}]
  ;(println "css-app prefix: " prefix " theme: " theme)
  (into []
        (reduce
         (fn [acc [kw v]]
           (concat acc (css-component prefix available kw v)))
         []
         current)))

(defn css-link [link]
  [:link {:class "webly"
          :rel "stylesheet"
          :type "text/css"
          :href link}])

(defn css-links 
  "this is used to get the css-links in the static html page"
  [prefix theme]
  (let [css-links (css-app prefix theme)]
    (doall (map css-link css-links))))