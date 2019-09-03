(ns nukr.infrastructure.profile-memory-repository)

(def table-name "profiles")

(defn add! [storage doc] (swap! storage update-in [table-name] conj doc))

(defn search-by-email [storage email] (let [profiles (filter #(= email (:email %)) (get @storage table-name))] (first profiles) ))

(defn get-all [storage] (get @storage table-name))