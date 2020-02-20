(ns app.web-example.core
  (:require
    ; route handlers
    [app.web-example.handler.example-database]
    [app.web-example.handler.example-react]
    [app.web-example.handler.index]
    ; react components
    [app.web-example.config.react-components]
    ; imports
    [app.lib.http-handler.core :as http-handler]
    [app.web-example.impl.handler :as handler]))

(set! *warn-on-reflection* true)


(defn example-routes
  []
  [["/" :route/index]
   ["/example-database" :route/example-database]
   ["/example-react" :route/example-react]])


(defn example-http-handler
  [config]
  (http-handler/webapp-http-handler handler/example-handler, (example-routes), config))
