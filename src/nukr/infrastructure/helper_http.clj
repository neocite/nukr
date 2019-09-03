(ns nukr.infrastructure.helper-http
  (:require [cheshire.core :as json] [clj-http.client :as http]))

(def default-port 3001)

(def default-host "http://localhost:")

(defn create-post [route body] (http/post (str default-host default-port route) {:content-type :json :throw-exceptions false :body (json/generate-string body)}))
(defn create-get [route params] (http/get (str default-host default-port route params) {:content-type :json :throw-exceptions false }))

(def http-created-code 201)
(def http-ok-code 200)
(def http-unprocessable-entity-code 422)

(defn http-created-json-response [body] {:status http-created-code
                                         :headers {"Content-Type" "application/json; charset=utf-8"}
                                         :body (json/generate-string body)})


(defn http-ok-json-response [body] {:status http-ok-code
                                         :headers {"Content-Type" "application/json; charset=utf-8"}
                                         :body (json/generate-string body)})

(defn http-invalid-json-response [] {     :status http-unprocessable-entity-code
                                          :headers {"Content-Type" "application/json; charset=utf-8"}
                                          :body "Unprocessable Entity"})