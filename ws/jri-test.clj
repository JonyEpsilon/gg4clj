;; gorilla-repl.fileformat = 1

;; **
;;; # Gorilla REPL
;;; 
;;; Welcome to gorilla :-)
;;; 
;;; Shift + enter evaluates code. Hit ctrl+g twice in quick succession or click the menu icon (upper-right corner) for more commands ...
;;; 
;;; It's a good habit to run each worksheet in its own namespace: feel free to use the declaration we've provided below if you'd like.
;; **

;; @@
(ns blessed-marsh
  (:require [gorilla-plot.core :as plot]
            [clojure.java.shell :as shell]
            [cemerick.pomegranate :as pomegranate]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def jri-path
  (second (re-matches #"\[1\] \"(.*)\"\n"
    (:out (shell/sh "Rscript" "--vanilla" "-e" "system.file(\"jri\", package=\"rJava\")")))))
jri-path
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;/Library/Frameworks/R.framework/Versions/3.1/Resources/library/rJava/jri&quot;</span>","value":"\"/Library/Frameworks/R.framework/Versions/3.1/Resources/library/rJava/jri\""}
;; <=

;; @@
(map pomegranate/add-classpath (file-seq (java.io.File. jri-path)))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"(nil nil nil nil nil nil nil nil nil nil nil nil nil)"}
;; <=

;; @@
(System/setProperty "java.library.path" (str jri-path ":" (System/getProperty "java.library.path")))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;/Users/jony/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.&quot;</span>","value":"\"/Users/jony/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.\""}
;; <=

;; @@
(System/getProperty "java.library.path")
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;/Library/Frameworks/R.framework/Versions/3.1/Resources/library/rJava/jri:/Users/jony/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.&quot;</span>","value":"\"/Library/Frameworks/R.framework/Versions/3.1/Resources/library/rJava/jri:/Users/jony/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.\""}
;; <=

;; @@
(org.rosuda.JRI.Rengine.)
;; @@

;; @@
(import org.rosuda.JRI.REngine)
;; @@

;; @@

;; @@
