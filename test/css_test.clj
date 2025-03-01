(ns css-test)

(ns css-test
  (:require
   [clojure.test :refer [deftest is are testing]]
   ;[cljs.test :refer-macros [async deftest is testing]]
   [frontend.css.links :refer [css-component css-app]]))

(def available-themes
  {:tailwind   {true ["tailwindcss/dist/tailwind.css"]}
   :codemirror {true ["codemirror/lib/codemirror.css"]
                "base16-light" ["codemirror/theme/base16-light.css"]}})

(def current-theme-config
  {:tailwind true
   :codemirror "base16-light"})

(deftest theme-component-config []
  (are [x y] (= x y)

    []
    (css-component "/r/" available-themes :codemirror false)

    ["/r/codemirror/lib/codemirror.css"]
    (css-component "/r/" available-themes :codemirror true)

    ["/r/codemirror/lib/codemirror.css"
     "/r/codemirror/theme/base16-light.css"]
    (css-component "/r/" available-themes :codemirror "base16-light")))

(deftest theme-app-config []
  (are [x y] (= x y)

    ["/r/tailwindcss/dist/tailwind.css"
     "/r/codemirror/lib/codemirror.css"
     "/r/codemirror/theme/base16-light.css"]
    (css-app "/r/" {:available available-themes 
                    :current current-theme-config})
;
    ))
