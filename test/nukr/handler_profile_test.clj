(ns nukr.handler-profile-test
  (:require [midje.sweet :refer :all]
            [nukr.handler :as handler]
            [ring.adapter.jetty :refer [run-jetty]]
            [cheshire.core :as json]
            [nukr.infrastructure.helper-web-server :as helper-web-server]
            [nukr.infrastructure.helper-http :as helper-http]))


(against-background [(before :facts (helper-web-server/start-server handler/app ))
                     (after :facts (helper-web-server/stop-server))]
  (fact "Create profile through API"
        (let [profile-request {:name "a"
                       :email "a@something.com"
                       :phone "+1-202-555-0140"}
              response (helper-http/create-post "/profile" profile-request)]
          (:status response) => helper-http/http-created-code
          (:body response) => (json/generate-string {:name "a"
                                                     :email "a@something.com"
                                                     :phone "+1-202-555-0140" }))))


(against-background [(before :facts (helper-web-server/start-server handler/app ))
                     (after :facts (helper-web-server/stop-server))]
                    (fact "Try create profile through API without name value"
                          (let [profile-request {:name ""
                                                 :email "a@something.com"
                                                 :phone "+1-202-555-0140"}
                                response (helper-http/create-post "/profile" profile-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))

(against-background [(before :facts (helper-web-server/start-server handler/app ))
                     (after :facts (helper-web-server/stop-server))]
                    (fact "Try create profile through API without name and email values"
                          (let [profile-request {:name ""
                                                 :email ""
                                                 :phone "+1-202-555-0140"}
                                response (helper-http/create-post "/profile" profile-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))

(against-background [(before :facts (helper-web-server/start-server handler/app ))
                     (after :facts (helper-web-server/stop-server))]
                    (fact "Try create profile through API without name, email and phone values"
                          (let [profile-request {:name ""
                                                 :email ""
                                                 :phone ""}
                                response (helper-http/create-post "/profile" profile-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))

(against-background [(before :facts (helper-web-server/start-server handler/app ))
                     (after :facts (helper-web-server/stop-server))]
                    (fact "Try create profile through API without name attribute"
                          (let [profile-request {:email "a@something.com"
                                                 :phone "+1-202-555-0140"}
                                response (helper-http/create-post "/profile" profile-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))

(against-background [(before :facts (helper-web-server/start-server handler/app ))
                     (after :facts (helper-web-server/stop-server))]
                    (fact "Try create profile through API without name and email attribute"
                          (let [profile-request {:phone "+1-202-555-0140"}
                                response (helper-http/create-post "/profile" profile-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))

(against-background [(before :facts (helper-web-server/start-server handler/app ))
                     (after :facts (helper-web-server/stop-server))]
                    (fact "Try create profile through API without name, email and phone attribute"
                          (let [profile-request {}
                                response (helper-http/create-post "/profile" profile-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))

(against-background [(before :facts (helper-web-server/start-server handler/app ))
                     (after :facts (helper-web-server/stop-server))]
                    (fact "Try create profile through API without name and email attributes"
                          (let [profile-request {:name ""
                                                 :email ""
                                                 :phone "+1-202-555-0140"}
                                response (helper-http/create-post "/profile" profile-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))

(against-background [(before :facts (helper-web-server/start-server handler/app ))
                     (after :facts (helper-web-server/stop-server))]
                    (fact "Try create profile through API without name, email and phone attributes"
                          (let [profile-request {:name ""
                                                 :email ""
                                                 :phone ""}
                                response (helper-http/create-post "/profile" profile-request)]
                            (:status response) => helper-http/http-unprocessable-entity-code
                            (:body response) => "Unprocessable Entity")))