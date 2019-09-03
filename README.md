# nukr

A new social network [![CircleCI](https://circleci.com/gh/neocite/nukr.svg?style=svg)](https://circleci.com/gh/neocite/nukr)

## Usage

#### Running tests

```
lein midje :autotest
```

#### Running application

```
lein clean

lein ring uberjar

java -jar target/nukr.jar 
```

#### Call APIs

######Create profile
```
curl -X POST -d '{"name" : "A", "email" : "a@something.com", "phone" :  "+1-202-555-0140"}' \
  -H "Content-Type: application/json" localhost:3000/profile
```

######Create link between profiles
```
curl -X POST -d '{"profile-email" : "a@something.com", "friend-profile-email" : "b@something.com"}' \
  -H "Content-Type: application/json" localhost:3000/link
```

######Get friend suggestions
```
curl "localhost:3000/friend-suggestions?profile-email=a@something.com"
```
