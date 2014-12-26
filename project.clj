;;;; This file is part of gg4clj. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; gg4clj is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(defproject gg4clj "0.1.0"
  :description "A simple wrapper for R's ggplot2 in Clojure and Gorilla REPL."
  :url "https://github.com/JonyEpsilon/gg4clj"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [gorilla-renderable "1.0.0"]]
  :plugins [[lein-gorilla "0.3.4"]])
