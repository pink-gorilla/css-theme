(ns frontend.css
  (:require
   [extension :refer [get-extensions]]))

(defn get-theme-config [exts]
  (let [themes (->> (get-extensions exts {:theme {:available {} :current {}}})
                    (map :theme))]
    {:available (reduce merge {} (map :available themes))
     :current (reduce merge {} (map :current themes))}))


(defn config-theme 
  "function that configures the css loader from extensions"
  [_module-name _config exts _default-config]
  (get-theme-config exts))
