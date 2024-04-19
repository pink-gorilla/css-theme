(ns frontend.css
  (:require
   [extension :refer [get-extensions]]))

(defn get-theme-config [exts]
  (let [themes (->> (get-extensions exts {:theme {:available {} :current {}}})
                    (map :theme))]
    {:available (reduce merge {} (map :available themes))
     :current (reduce merge {} (map :current themes))}))


(defn config-theme [_module-name _config exts _default-config]
  ;(let [module-name (if (string? module-name)
  ;                    (keyword module-name)
  ;                    module-name)]
  ;  (or (get config module-name) default-config)) 
  (get-theme-config exts))
