(ns app.web-homepage.handler.index-lang
  (:require
    [app.lib.util.ring :as ring-util]
    [app.web-homepage.impl.handler :as impl]))

(set! *warn-on-reflection* true)


(defmethod impl/homepage-handler :route/index-lang
  [request]
  (when-let [lang (#{"ru" "en"} (get-in request [:params :lang]))]
    (ring-util/plain-text-response (str "Homepage: " lang))))
