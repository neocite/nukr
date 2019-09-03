(ns nukr.handler-link-test
  (:require [midje.sweet :refer :all]
            [nukr.handler :as handler]
            [ring.adapter.jetty :refer [run-jetty]]
            [cheshire.core :as json]
            [nukr.infrastructure.helper-web-server :as helper-web-server]
            [nukr.infrastructure.helper-http :as helper-http]))


(defn create-profiles []
  (helper-http/create-post "/profile" {:name "b"  :email "b@something.com" :phone "+1-202-555-0141"})
  (helper-http/create-post "/profile" {:name "c" :email "c@something.com" :phone "+1-202-555-0142"}))

(against-background [(before :facts [(helper-web-server/start-server handler/app) (create-profiles)])
                     (after :facts (helper-web-server/stop-server))]

                    (fact "Create link between two profile through API"
                          (let [link-request {:profile-email "b@something.com"
                                              :friend-profile-email "c@something.com"}
                                expected-result {:name "b"
                                                 :email "b@something.com"
                                                 :phone "+1-202-555-0141"
                                                 :friends {{:name "c"
                                                            :email "c@something.com"
                                                            :phone "+1-202-555-0142"} {} }}
                                response (helper-http/create-post "/link" link-request)]
                            (:status response) => helper-http/http-created-code
                            (:body response) => (json/generate-string expected-result ))))


(against-background [(before :facts [(helper-web-server/start-server handler/app) (create-profiles)])
                     (after :facts (helper-web-server/stop-server))]

                    (fact "Try create link between two profile through API without profile email value"
                          (let [link-request {:profile-email ""
                                              :friend-profile-email "c@something.com"}
                                response (helper-http/create-post "/link" link-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))

(against-background [(before :facts [(helper-web-server/start-server handler/app) (create-profiles)])
                     (after :facts (helper-web-server/stop-server))]

                    (fact "Try create link between two profile through API without profile email value"
                          (let [link-request {:friend-profile-email "c@something.com"}
                                response (helper-http/create-post "/link" link-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))

(against-background [(before :facts [(helper-web-server/start-server handler/app) (create-profiles)])
                     (after :facts (helper-web-server/stop-server))]

                    (fact "Try create link between two profile through API without profile email and friend profile email values"
                          (let [link-request {:profile-email ""
                                              :friend-profile-email ""}
                                response (helper-http/create-post "/link" link-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))

(against-background [(before :facts [(helper-web-server/start-server handler/app) (create-profiles)])
                     (after :facts (helper-web-server/stop-server))]

                    (fact "Try create link between two profile through API without profile email and friend profile email attributes"
                          (let [link-request {}
                                response (helper-http/create-post "/link" link-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))


(against-background [(before :facts [(helper-web-server/start-server handler/app) (create-profiles)])
                     (after :facts (helper-web-server/stop-server))]

                    (fact "Try create link between two profile through API without profile email and friend profile email"
                          (let [link-request {:profile-email ""
                                              :friend-profile-email ""}
                                response (helper-http/create-post "/link" link-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))