(ns my-app.build.time-reporting)

(defn get-time-in-ms-now
  []
  (System/currentTimeMillis))

(defn get-time-in-seconds
  [elapsed]
  (with-precision 2
    (/ (double elapsed) 1000)))

(defn get-elapsed-time-in-seconds
  [starting-time ending-time]
  (get-time-in-seconds (- ending-time starting-time)))

(defn measure-and-report-elapsed-time
  [preamble-message started-at]
  (let [finished-at (get-time-in-ms-now)
        elapsed-seconds (get-elapsed-time-in-seconds
                         started-at
                         finished-at)
        expression-string (str
                           preamble-message
                           elapsed-seconds
                           " seconds")]
    (println expression-string)))
