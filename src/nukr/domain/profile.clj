(ns nukr.domain.profile)

(defn new [name email phone]
  { :name    name
    :email   email
    :phone   phone})

(defn add-friend [profile friend]
  (if (or (nil? friend) (nil? profile))
    ()
    (assoc-in profile [:friends friend] {})))