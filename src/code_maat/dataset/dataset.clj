(ns code-maat.dataset.dataset
  (:require [incanter.core :as incanter]
            [code-maat.analysis.workarounds :as workarounds]))

;;; This module contains a thin layer around a subset of Incanter core.
;;; The reason is that Incanter is inconsistent in its return values:
;;;  - for multiple hits, a seq is returned,
;;;  - for a single hit, the sole value is returned.
;;; This behavior introduces a special case that we want to hide
;;; from our application level code.
;;;
;;; Thus, the responsibility of this module is to provide a uniform API by
;;; always returning a seq.

(defn -empty?
  [ds]
  (= 0 (incanter/nrow ds)))

(defn -group-by
  [group-criterion ds]
  (if (-empty? ds)
    []
    (incanter/$group-by group-criterion ds)))

(defn -select-by
  [criterion ds]
  (workarounds/fix-single-return-value-bug
   (incanter/$ criterion ds)))