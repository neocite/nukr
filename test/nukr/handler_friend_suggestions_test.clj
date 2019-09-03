(ns nukr.handler-friend-suggestions-test
  (:require [midje.sweet :refer :all]
            [nukr.handler :as handler]
            [ring.adapter.jetty :refer [run-jetty]]
            [cheshire.core :as json]
            [nukr.infrastructure.helper-web-server :as helper-web-server]
            [nukr.infrastructure.helper-http :as helper-http]))


(defn create-profiles []
  (helper-http/create-post "/profile" {:name "b"  :email "b@something.com" :phone "+1-202-555-0141"})
  (helper-http/create-post "/profile" {:name "d" :email "d@something.com" :phone "+1-202-555-0143"})
  (helper-http/create-post "/profile" {:name "e" :email "e@something.com" :phone "+1-202-555-0144"})
  (helper-http/create-post "/profile" {:name "f" :email "f@something.com" :phone "+1-202-555-0145"})
  (helper-http/create-post "/profile" {:name "g" :email "g@something.com" :phone "+1-202-555-0146"})
  (helper-http/create-post "/profile" {:name "h" :email "h@something.com" :phone "+1-202-555-0147"}))

(defn create-link-between-profiles []
  (helper-http/create-post "/link" {:profile-email "b@something.com" :friend-profile-email "d@something.com" })
  (helper-http/create-post "/link" {:profile-email "b@something.com" :friend-profile-email "h@something.com" })
  (helper-http/create-post "/link" {:profile-email "b@something.com" :friend-profile-email "e@something.com" })
  (helper-http/create-post "/link" {:profile-email "g@something.com" :friend-profile-email "f@something.com" }))

(against-background [(before :facts [(helper-web-server/start-server handler/app) (create-profiles) (create-link-between-profiles)])
                     (after :facts (helper-web-server/stop-server))]

                    (fact "Get friend suggestions by email through API"
                          (let [expected-result [{:name "h" :email "h@something.com" :phone "+1-202-555-0147"}
                                                 {:name "e" :email "e@something.com" :phone "+1-202-555-0144"}]
                                response (helper-http/create-get "/friend-suggestions" "?profile-email=d@something.com")]
                            (:status response) => helper-http/http-ok-code
                            (:body response) => (json/generate-string expected-result ))))

(against-background [(before :facts [(helper-web-server/start-server handler/app)])
                     (after :facts (helper-web-server/stop-server))]

                    (fact "Get friend suggestions without email parameter through API"
                          (let [response (helper-http/create-get "/friend-suggestions" "")]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))

(against-background [(before :facts [(helper-web-server/start-server handler/app)])
                     (after :facts (helper-web-server/stop-server))]

                    (fact "Get null friend through API"
                          (let [response (helper-http/create-get "/friend-suggestions" "?profile-email=m@something.com")]
                            (:status response) => helper-http/http-ok-code
                            (:body response) => "[]")))