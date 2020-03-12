(ns dev.dev-system.unit.shadow-cljs
  (:require
    [app.lib.util.exec :as exec]
    [clojure.tools.logging :as log]
    [integrant.core :as ig]
    [shadow.cljs.devtools.api :as shadow]
    [shadow.cljs.devtools.server :as server]))

(set! *warn-on-reflection* true)


(defn- start-shadow!
  [{:keys [builds-to-start] :as options}]
  (log/info "Start Shadow CLJS" options)
  (server/start!)
  (doseq [build builds-to-start]
    (shadow/watch build)))


(defn- stop-shadow!
  [system]
  (log/info "Stop Shadow CLJS" system)
  (server/stop!))


(defmethod ig/init-key :dev-system/ref'shadow-cljs
  [_ options]
  (exec/future (start-shadow! options)))


(defmethod ig/halt-key! :dev-system/ref'shadow-cljs
  [_ ref'system]
  (exec/future (stop-shadow! @ref'system)))


#_(comment
    (server/start!)
    (server/stop!)
    (foo :example)
    (shadow/compile :example)
    (shadow/repl :example)
    (shadow/watch :example)
    (shadow/watch-compile-all!))

