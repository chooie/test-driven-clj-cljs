(ns karma-cljs.core
  (:require
   [cljs.pprint :as pprint]
   [cljs.test :as cljs-test]
   [clojure.data :as data]
   [clojure.string :as string]
   [karma-cljs.core-test :as my-test]
   )
  (:require-macros
   [karma-cljs.macros :as karma-cljs-macros]
   ))

(def karma (volatile! nil))

(defn karma?
  []
  (not (nil? @karma)))

(defn- karma-info!
  "For reporting other data. E.g. number of tests or debugging messages"
  [m]
  (.info @karma (clj->js m)))

(defn- karma-result!
  "For indicating that a single test has finished"
  [m]
  (.result @karma (clj->js m)))

(defn- coverage-result
  []
  #js {"coverage" (aget js/window "__coverage__")})

(defn- karma-complete!
  "The client completed execution of all tests"
  []
  (.complete @karma (coverage-result)))

(defn- get-date-now []
  (.getTime (js/Date.)))

(defn- indent [number-of-spaces the-string]
  (let [indentation (reduce str "" (repeat number-of-spaces " "))]
    (string/replace the-string #"\n" (str "\n" indentation))))

(defn- remove-last-new-line [the-string]
  (subs the-string 0 (dec (count the-string))))

(defn- format-fn [indentation [c & q]]
  (let [e (->> q
               (map #(with-out-str (pprint/pprint %)))
               (apply str)
               (str "\n"))]
    (str "(" c (indent (+ indentation 2) (remove-last-new-line e)) ")")))

(defn- format-diff [indentation assert [c a b & q]]
  (when (and (= c '=) (= (count assert) 3) (nil? q))
    (let [format (fn [sign value]
                   (str sign " "
                        (if value
                          (indent (+ indentation 2)
                                  (-> value
                                      (pprint/pprint)
                                      (with-out-str)
                                      (remove-last-new-line)))
                          "\n")))
          [removed added] (data/diff a b)]
      (str (format "-" removed)
           (format (str "\n" (apply str (repeat indentation " ")) "+")
                   added)))))

(defn- format-log
  [{:keys [expected actual message testing-contexts-str] :as result}]
  (let [indentation (count "expected: ")]
    (str
      "FAIL in   " (cljs-test/testing-vars-str result) "\n"
      (when-not (string/blank? testing-contexts-str)
        (str "\"" testing-contexts-str "\"\n"))
      (if (and (seq? expected)
               (seq? actual))
        (str
          "expected: " (format-fn indentation expected) "\n"
          "  actual: " (format-fn indentation (second actual)) "\n"
          (when-let [diff (format-diff indentation expected (second actual))]
            (str "    diff: " diff "\n")))
        (str
          expected " failed with " actual "\n"))
      (when message
        (str " message: " (indent indentation message) "\n")))))

(def test-var-result (volatile! []))

(def test-var-time-start (volatile! (get-date-now)))

(defmethod cljs-test/report :karma
  [_])

;; By default, all report types for :cljs.test reporter are printed
(derive :karma :cljs-test/default)

(defmethod cljs-test/report [:karma :summary]
  [_]
  ;; Do not print "Testing <ns>"
  )

(defmethod cljs-test/report [:karma :begin-test-ns]
  [m]
  ;; Do not print "Testing <ns>"
  )

(defmethod cljs-test/report [:karma :begin-test-var]
  [_]
  (vreset! test-var-time-start (get-date-now))
  (vreset! test-var-result []))

(defmethod cljs-test/report [:karma :end-test-var]
  [m]
  (let [var-meta (meta (:var m))
        result   {"suite"       [(:ns var-meta)]
                  "description" (:name var-meta)
                  "success"     (zero? (count @test-var-result))
                  "skipped"     nil
                  "time"        (- (get-date-now) @test-var-time-start)
                  "log"         (map format-log @test-var-result)}]
    (karma-result! result)))

(defmethod cljs-test/report [:karma :fail]
  [m]
  (cljs-test/inc-report-counter! :fail)
  (vswap! test-var-result conj (assoc m
                                      :testing-contexts-str
                                      (cljs-test/testing-contexts-str))))

(defmethod cljs-test/report [:karma :error]
  [m]
  ;; TODO: Should this call karma.error?
  (cljs-test/inc-report-counter! :error)
  (vswap! test-var-result conj (assoc m
                                      :testing-contexts-str
                                      (cljs-test/testing-contexts-str))))

(defmethod cljs-test/report [:karma :end-run-tests]
  [_]
  (karma-complete!))

(defn start
  [karma-test-manager total-count]
  (vreset! karma karma-test-manager)
  (karma-info! {:total total-count}))

(defn ^:export start-running-tests
  [karma]
  (.log js/console "heyooo")
  (karma-cljs-macros/run-all-tests
   karma
   #"(karma-cljs)\..*-test"))
