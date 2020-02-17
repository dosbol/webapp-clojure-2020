(ns dev.dev-system.unit.watcher
  (:require
    [app.lib.util.exec :as exec]
    [clojure.tools.logging :as log]
    [hara.io.watch :as watch]
    [integrant.core :as ig]))

(set! *warn-on-reflection* true)


(defn start-watcher
  [handler, {:keys [dirs files exclude] :as options}]
  (log/info "Start watcher" options)
  (watch/start-watcher
    (watch/watcher dirs
      (fn [& args] (handler args))
      {#_#_:types #{:modify}
       :filter files
       :exclude exclude
       :mode :async})))


(defn stop-watcher
  [watcher]
  (log/info "Stop watcher" watcher)
  (watch/stop-watcher watcher))


#_(comment
    (time
      (let [w (time (start-watcher (fn [& reason] (println reason))

                      {:dirs ["src" "resources/app" "dev" "dev-resources/dev"]

                       ; http://docs.caudate.me/hara/hara-io-watch.html#watch-options
                       ; :filter will pick out only files that match this pattern.
                       :files [".props$" ".clj$" ".cljc$" ".js$" ".xml$"
                               ".sql$" ".properties$" ".mustache$" ".yaml"]

                       ; http://docs.caudate.me/hara/hara-io-watch.html#watch-options
                       ; :exclude will leave out files that match this pattern.
                       :exclude []}))]

        (time (stop-watcher w)))))


(defmethod ig/init-key :dev-system/watcher
  [_ {:keys [handler, options, run-handler-on-init?]}]
  (let [watcher (start-watcher handler options)]
    (when run-handler-on-init?
      (exec/try-log-error ["Run handler on init" handler (pr-str options)]
        (handler :init-watcher)))
    watcher))


(defmethod ig/halt-key! :dev-system/watcher
  [_ watcher]
  (stop-watcher watcher))
