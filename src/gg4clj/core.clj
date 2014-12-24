(ns gg4clj.core
  (:import (java.io File))
  (:require [clojure.java.shell :as shell]
            [clojure.string :as string]))


;; * Functions for building R code *

(declare to-r)

(defn- quote-string
  "Wraps a string in escaped quotes."
  [st]
  (str "\"" st "\""))

(defn- function-name
  "R operators can be called in prefix form with a function name that is the quoted string
  of the operator name. This function handles a selection of the operators as special cases."
  [f]
  (case f
    :+ (quote-string "+")
    :<- (quote-string "<-")
    (name f)))

(defn- fn-from-vec
  "An R function call is represented by a Clojure vector with the function name, given as a keyword, in
  the first element. Subsequent elements can be used to represent positional or named arguments (see below).
  This function transforms one of these function-call vectors into the equivalent R code, returned as a string."
  [vec]
  (str (function-name (first vec)) "("
       (string/join ", " (map to-r (rest vec)))
       ")"))

(defn- named-args-from-map
  "Named arguments to R functions are specified as Clojure maps. This function constructs the snippet of
  the argument string corresponding to the given named arguments. Note that the argument order may not be
  the same as specified when the map is created."
  [arg-map]
  (string/join ", " (map #(str (name %) " = " (to-r (% arg-map))) (keys arg-map))))

(defn to-r
  "Takes a Clojure representation of R code, and returns the corresponding R code as a string."
  [code]
  (cond
    ;; vectors are either function calls or lists of commands
    (vector? code) (if (vector? (first code))
                     (string/join ";\n" (map to-r code))
                     (fn-from-vec code))
    (map? code) (named-args-from-map code)
    (keyword? code) (name code)
    (string? code) (quote-string code)
    true (pr-str code)))

(defn data-frame
  "A helper function that takes frame-like data in the 'natural' Clojure format of
  {:key [vector of values] :key2 [vector ...] ...} and returns the Clojure representation
  of R code to make a data.frame."
  [data-map]
  [:data.frame
   (apply hash-map (mapcat (fn [e] [(key e) (into [:c] (val e))]) data-map))])

;; * Functions for driving R *

(defn- rscript
  "Execute a file of R code in a new R session. No output will be returned. If the R process exits abnormally, then the
  error output will be printed to the console."
  [script-path]
  (let [return-val (shell/sh "Rscript" "--vanilla" script-path)]
    ;; rscript is quite chatty, so only pass on err text if exit was abnormal
    (when (not= 0 (:exit return-val))
      (println (:err return-val)))))


;; * Wrappers for ggplot2 functions *

(defn- wrap-ggplot
  "Wraps the given R command with commands to load ggplot2 and save the last plot to the given file."
  [command filepath]
  (to-r
    [[:library :ggplot2]
     command
     [:ggsave {:filename filepath :width 5 :height 3}]]))

(defn render-ggplot
  [plot-command]
  (let [r-file (File/createTempFile "gorilla" ".r")
        r-path (.getAbsolutePath r-file)
        ;;_ (println r-path)
        out-file (File/createTempFile "gorilla" ".svg")
        out-path (.getAbsolutePath out-file)
        _ (spit r-path (wrap-ggplot plot-command out-path))
        _ (rscript r-path)
        rendered-plot (slurp out-path)
        _ (.delete r-file)
        _ (.delete out-file)]
    rendered-plot))