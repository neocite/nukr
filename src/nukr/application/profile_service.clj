(ns nukr.application.profile-service
  (:require [nukr.domain.profile :as profile]
            [nukr.infrastructure.profile-memory-repository :as profile-repository]))

(defn add [storage name email phone]
  (let [profile (profile/new name email phone )]
    (profile-repository/add! storage profile)
    profile))

(defn add-friend! [storage profile-email friend-profile-email]
  (let [profile (profile-repository/search-by-email storage profile-email)
        friend (profile-repository/search-by-email storage friend-profile-email)
        new-connection (profile/add-friend profile friend)]



      (profile-repository/add! storage new-connection) new-connection))

(defn- get-first-item-but-me-by-email [collection email]
  (map (fn [item] (if (not= email (:email (first item))) (first item))) collection))

(defn- get-friends [collection email]
  (map (fn [profile] (get-first-item-but-me-by-email (:friends (first profile)) email)) collection))

(defn- remove-nils [collection]
  (remove nil? collection))

(defn- get-all-linked-me [collection email]
  (map (fn [item] (map (fn [sub-item] (if (= email (:email (first sub-item))) item) )  (:friends item))) collection))


(defn get-friend-suggestions [storage email-profile]
  (-> (profile-repository/get-all storage)
      (get-all-linked-me email-profile)
      (get-friends email-profile)
      (flatten)
      (distinct)
      (remove-nils)))