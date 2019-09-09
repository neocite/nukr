(ns nukr.application.profile-service-test
  (:require [midje.sweet :refer :all]
            [nukr.application.profile-service :as profile-service]))

(def storage (atom {}))

(fact "Create profile through service"
      (profile-service/add storage "A" "a@something.com" "+1-202-555-0140") => (just {  :name "A"
                                                                                        :email "a@something.com"
                                                                                        :phone "+1-202-555-0140" }))

(def b (profile-service/add storage "B" "b@something.com" "+1-202-555-0141"))
(def d (profile-service/add storage "D" "d@something.com" "+1-202-555-0143"))


(fact "Add friend in profile"
      (profile-service/add-friend! storage (:email b) (:email d)) => (just { :name "B"
                                                                            :email "b@something.com"
                                                                            :phone "+1-202-555-0141"
                                                                            :friends {{ :name "D"
                                                                                        :email "d@something.com"
                                                                                        :phone "+1-202-555-0143" } {}} }))
(def t {:email "t@something.com"})
(fact "Add friend in invalid profile"
      (profile-service/add-friend! storage (:email t) (:email d)) => (just ()))

(fact "Add invalid friend in profile"
      (profile-service/add-friend! storage (:email d) (:email t)) => (just ()))

(fact "Add invalid friend in invalid profile"
      (profile-service/add-friend! storage (:email t) (:email t)) => (just ()))

(def e (profile-service/add storage "E" "e@something.com" "+1-202-555-0144"))
(def f (profile-service/add storage "F" "f@something.com" "+1-202-555-0145"))
(def g (profile-service/add storage "G" "g@something.com" "+1-202-555-0146"))
(def h (profile-service/add storage "H" "h@something.com" "+1-202-555-0147"))

(profile-service/add-friend! storage (:email b) (:email h))
(profile-service/add-friend! storage (:email b) (:email e))
(profile-service/add-friend! storage (:email g) (:email f))

(fact "Get suggestions from friends"
      (profile-service/get-friend-suggestions storage (:email d)) =>
      (just {:email "h@something.com" :name "H" :phone "+1-202-555-0147"}
            {:email "e@something.com" :name "E" :phone "+1-202-555-0144"}))