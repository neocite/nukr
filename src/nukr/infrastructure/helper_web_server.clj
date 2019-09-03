(ns nukr.infrastructure.helper-web-server
  (:require [ring.adapter.jetty :refer [run-jetty]]))

(def server (atom nil))

(def default-port 3001)

(defn start-server [app]
  (swap! server
         (fn [_] (run-jetty app {:port default-port :join? false}))))

(defn stop-server []
  (.stop @server))