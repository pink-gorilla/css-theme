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
        ; override current config from user config
        default-config (:current theme-config)
        default-keys (keys default-config)
        user-config (or (get config :css/theme) {})
        user-config (select-keys user-config default-keys)
        current-config (merge default-config user-config)
        theme-config (assoc theme-config :current current-config)]
    ;(println "config: " config)
    ;(println "user config: " user-config)
    ;(println "current config: " current-config)
    (write-edn-private :css-theme-config theme-config)
    theme-config))
