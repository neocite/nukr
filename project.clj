(defproject nukr "0.1.0-SNAPSHOT"
  :description "Nukr, a new social network"
  :license {:name "Apache License, Version 2.0"}
  :plugins [[lein-ring "0.12.5"]]
  :uberjar-name "nukr.jar"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.cli "0.4.1"]
                 [compojure "1.6.1"]
                 [ring/ring-json "0.4.0"]
                 [clj-http "3.10.0"]
                 [lein-cloverage "1.0.7-SNAPSHOT"]
                 [ring/ring-defaults "0.3.2"]]
  :ring {:handler nukr.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]
                        [midje "1.9.8"]
                        [ring/ring-core "1.7.1"]
                        [ring/ring-jetty-adapter "1.7.1"]
                        [cheshire "5.8.1"]]
         :plugins [[lein-midje "3.2.1"]
                   [jonase/eastwood "0.3.5"]
                   [lein-bikeshed "0.5.2"]
                   [lein-kibit "0.1.6"]
                   [lein-cloverage "1.0.13"]]}})
