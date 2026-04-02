(ns demo
  (:require
   [extension :refer [discover]]
   [frontend.css :refer [config-theme]]))


(defn demo [{:keys [config profile version] :as opts}]
  (let [exts (discover)]
    (config-theme nil config exts nil)))



