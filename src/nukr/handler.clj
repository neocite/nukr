(ns nukr.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :as json]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]
            [nukr.infrastructure.helper-http :as http-wrapper]
            [nukr.application.profile-service :as profile-service]))

(def storage (atom {}))

(defn can-create-profile? [body]
      (and  (not= "" (:name body))
            (contains? body :name)
            (not= "" (:phone body))
            (contains? body :phone)
            (not= "" (:email body))
            (contains? body :email)))

(defn can-create-link? [body]
  (and  (not= "" (:profile-email body))
        (contains? body :profile-email)
        (not= "" (:friend-profile-email body))
        (contains? body :friend-profile-email)))

(defn can-create-search? [params]
  (and  (not= "" (:profile-email params))
        (contains? params :profile-email)))

(defroutes app-routes
  (POST "/profile" data-request
                                (if (can-create-profile? (:body data-request))

                                    (-> (profile-service/add storage
                                                         (:name (:body data-request))
                                                         (:email (:body data-request))
                                                         (:phone (:body data-request)))
                                      (http-wrapper/http-created-json-response))

                                    (http-wrapper/http-invalid-json-response)))

  (POST "/link" data-request
                                (if (can-create-link? (:body data-request))

                                    (-> (profile-service/add-friend storage (:profile-email (:body data-request))
                                                                            (:friend-profile-email (:body data-request)))
                                      (http-wrapper/http-created-json-response))

                                    (http-wrapper/http-invalid-json-response)))
  (GET "/friend-suggestions" params
                                (if (can-create-search? (:params params) )

                                    (-> (profile-service/get-friend-suggestions storage (:profile-email (:params params)))
                                      (http-wrapper/http-ok-json-response))

                                    (http-wrapper/http-invalid-json-response))))



(def app
  (-> (wrap-defaults app-routes api-defaults)
      (wrap-json-body {:keywords? true })))