(ns frontend.css
  (:require
   [extension :refer [get-extensions]]
   [modular.writer :refer [write-edn-private]]))

(defn get-theme-config [exts]
  (let [themes (->> (get-extensions exts {:theme {:available {} :current {}}})
                    (map :theme))]
    {:available (reduce merge {} (map :available themes))
     :current (reduce merge {} (map :current themes))}))


(defn config-theme
  "function that configures the css loader from extensions"
  [_module-name config exts _default-config]
  (let [; build the default theme config from extensions
        theme-config (get-theme-config exts)
        ; extend available from app config
        available-app (or (:available (get config :css/theme)) {})
        available (merge (:available theme-config) available-app)
        ; extend current from app config
        available-keys (keys available)
        current-app (or (:current (get config :css/theme)) {})
        current-app (select-keys current-app available-keys)
        current-exts (:current theme-config)
        current (merge current-exts current-app)
        ; put together
        theme-config (assoc theme-config :available available :current current)]
    (write-edn-private :css-theme-config theme-config)
    theme-config))



