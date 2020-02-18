(ns app.web-homepage.impl.html-page
  (:require
    [app.lib.util.html :as html]
    [app.lib.util.ring :as ring-util]
    [mount.core :as mount]
    [rum.core :as rum]))

(set! *warn-on-reflection* true)


(mount/defstate ^{:on-reload :noop} styles-css-uri
  :start
  (html/static-uri-with-hash "/app/homepage/main.css"))


(defn render-html
  [hiccup]
  (str "<!DOCTYPE html>\n"
    (rum/render-static-markup hiccup)))


(defn response
  [hiccup]
  (-> hiccup
    (render-html)
    (ring-util/html-response)))

