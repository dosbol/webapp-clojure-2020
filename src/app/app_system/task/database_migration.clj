(ns app.app-system.task.database-migration
  (:require
    [app.lib.database.liquibase :as liquibase]
    [app.lib.util.exec :as exec]
    [clojure.tools.logging :as log]
    [integrant.core :as ig]))

(set! *warn-on-reflection* true)


(defmethod ig/init-key :app-system.task/*database-migration
  [_ {:keys [*data-source changelog-path enabled?] :as config}]
  (log/info "Database migrations" (pr-str config))
  (exec/future
    (when enabled?
      (liquibase/update-database @*data-source, changelog-path))))
