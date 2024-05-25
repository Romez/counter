(ns counter.app
  (:gen-class)
  (:import [rendering Render]))

(defn -main [& _args]
  (.hello (Render.))
  0)

