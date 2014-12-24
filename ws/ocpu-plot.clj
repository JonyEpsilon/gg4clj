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
(ns flowing-sunrise
  (:require [gorilla-plot.core :as plot]
            [gorilla-repl.html :as html]
            [gorilla-repl.image :as image]
            [opencpu-clj.core :as opencpu]
            [opencpu-clj.ocpu :as ocpu]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn qplot
  [spec]
  (let [p (ocpu/object "http://localhost:4425" :library "ggplot2" :R "qplot" spec :svg)
       ;; _ (println p)
        s (second (:result p))
        g (ocpu/session "http://localhost:4425" s :svg)
        r (:result g)]
     (html/html-view r)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;flowing-sunrise/qplot</span>","value":"#'flowing-sunrise/qplot"}
;; <=

;; @@
(qplot
  {:x "price" :y "carat" :data "diamonds" :geom "smooth"})
;; @@
;; =>
;;; {"type":"html","content":"Invalid URL: /m/svg \nTry: /ocpu \n","value":"#gorilla_repl.html.HtmlView{:content \"Invalid URL: /m/svg \\nTry: /ocpu \\n\"}"}
;; <=

;; @@
(ns opencpu-clj.ocpu)
(defn session
  "Does a call to the OpenCPU 'session' endpoint.
   base-url : url and port of the OpenCPU server
   session-path : The path to the session object, as returned from the 'object' function.
   Returns a map with keys :result and :status , containing the result of the call or an error message.
   The value of :status is the http status code."
  [base-url session-path output-format]
  (get-body (format "%s/%s/%s?width=8&height=4" base-url session-path (name output-format))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;opencpu-clj.ocpu/session</span>","value":"#'opencpu-clj.ocpu/session"}
;; <=

;; @@
(defn qplot
  [spec]
  (let [p (ocpu/object "http://localhost:4425"
             :library "base" :R "print"
             {:x "library(ggplot2); f <- data.frame(x = 1, y = 1:10); print(qplot(y, data = f))"} :svg)
       ;; _ (println p)
        s (second (:result p))
        g (ocpu/session "http://localhost:4425" s :svg)
        r (:result g)]
     (html/html-view r)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;flowing-sunrise/qplot</span>","value":"#'flowing-sunrise/qplot"}
;; <=

;; @@
(qplot {})
;; @@
;; =>
;;; {"type":"html","content":"This call did not generate any plot. Make sure the function/object produces a graph.\n","value":"#gorilla_repl.html.HtmlView{:content \"This call did not generate any plot. Make sure the function/object produces a graph.\\n\"}"}
;; <=

;; @@

;; @@
