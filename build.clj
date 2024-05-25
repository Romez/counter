(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'counter/app)
(def version (format "1.3.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")
(def jar-file (format "target/%s-%s.jar" (name lib) version))
(def uber-file (format "target/%s-%s-standalone.jar" (name lib) version))

;; delay to defer side effects (artifact downloads)
(def basis (delay (b/create-basis {:project "deps.edn"})))

(defn clean [_]
  (b/delete {:path "target"}))

(defn jar [_]
  (b/write-pom {:class-dir class-dir
                :lib lib
                :version version
                :basis @basis
                :src-dirs ["src"]})
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file jar-file}))

(defn java [_]
  (b/javac {:src-dirs ["java"]
            :class-dir class-dir
            :basis @basis
            :javac-opts ["--release" "11"]}))

(defn uber [_]
  (clean nil)
  (java nil)
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  (b/compile-clj {:basis @basis
                  :ns-compile '[counter.app]
                  :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis @basis
           :main 'counter.app}))


