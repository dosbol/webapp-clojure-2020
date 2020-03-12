(ns app.lib.react.component)

#?(:clj (set! *warn-on-reflection* true) :cljs (set! *warn-on-infer* true))


(defmulti create-component
  "Component constructor by ID keyword."
  ::component-id)


(defmethod create-component :default
  [data]
  (println "Calling default `create-component` for" data))


(defn component-id
  "Get component ID."
  [data]
  (::component-id data))


(defn instance-id
  "Instance ID to differentiate several components with same component.
  Optional, defaults to `component-id`."
  [data]
  (or (::instance-id data)
      (component-id data)))


(defn set-component-id
  "Set component ID."
  [data comp-id]
  (assoc data ::component-id comp-id))


(defn set-instance-id
  "Set component instance ID."
  [data instance-id]
  (assoc data ::instance-id instance-id))
