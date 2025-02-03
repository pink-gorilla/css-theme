(ns frontend.css
  (:require
   [reagent.core :as r]
   [frontend.css.links :refer [css-app]]
   [frontend.css.dom :refer [existing-css update-css loading-a]]
   ))

(defonce theme-a
  (r/atom {:available {}
           :current {}}))

(defn add-components [{:keys [available current]}]
  (reset! theme-a
          {:available (merge available (:available @theme-a))
           :current (merge current (:current @theme-a))}))


(defn set-theme-component [component theme]
  (swap! theme-a assoc-in [:current component] theme))

(defn get-theme-component [component]
  (get-in @theme-a [:current component]))


(def loading? (r/reaction (pos? (count @loading-a))))

(defn css-loader [prefix]
  (r/with-let [css-links (r/reaction (css-app prefix @theme-a))]
    (when (not (empty? @css-links))
      ;(println "css has changed to: " @css-links)
      (update-css @css-links))
    [:div.webly-css-loader]))
