(ns clojure-exercises.answers.breadth-first
  (:require [midje.sweet :refer :all])
  (:import (clojure.lang PersistentQueue LazySeq)))

(defn tree-seq-bf [children root]
  ((fn step [queue]
     (lazy-seq
       (when (seq queue)
         (let [node (peek queue)]
           (cons node (step (into (pop queue) (children node))))))))
    (conj PersistentQueue/EMPTY root)))

(facts "about tree-seq-bf"
  (class (tree-seq-bf (constantly nil) 0)) => LazySeq
  (take 10 (tree-seq-bf (juxt dec inc) 0))
  => [0 -1 1 -2 0 0 2 -3 -1 -1]
  (tree-seq-bf (constantly nil) 0) => [0]
  ;;     1
  ;;    / \
  ;;   2   3
  ;;  / \  |
  ;; 4   5 6
  (map first (tree-seq-bf next [1 [2 [4] [5]] [3 [6]]]))
  => [1 2 3 4 5 6]
  ;;     :a
  ;;     / \
  ;;   :b  :c
  ;;   / \  |
  ;; :d  :e :f
  (tree-seq-bf {:a [:b :c], :b [:d :e], :c [:f]} :a)
  => [:a :b :c :d :e :f])
