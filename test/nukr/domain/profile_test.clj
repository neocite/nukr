(ns nukr.domain.profile-test
  (:require [midje.sweet :refer :all]
            [nukr.domain.profile :as profile]))

(fact "Create profile"
      (profile/new "Darth Vader" "darth.vader@disney.com" "+1-202-555-0140") => (just { :name "Darth Vader"
                                                                                        :email "darth.vader@disney.com"
                                                                                        :phone "+1-202-555-0140"}))
(def darth-vader {:name "Darth Vader"
                  :email "darth.vader@disney.com"
                  :phone "+1-202-555-0140" })

(def storm-trooper {:name "Storm Trooper"
                    :email "storm.trooper@disney.com"
                    :phone "+1-202-555-0141" })

(fact "Add friend"
      (profile/add-friend darth-vader storm-trooper) => (just { :name "Darth Vader"
                                                                  :email "darth.vader@disney.com"
                                                                  :phone "+1-202-555-0140"
                                                                  :friends {{ :name "Storm Trooper"
                                                                              :email "storm.trooper@disney.com"
                                                                              :phone "+1-202-555-0141" } {}}} ))