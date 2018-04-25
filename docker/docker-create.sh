docker network create --subnet=172.18.0.0/24 tb-net

docker container stop zuul-cms
docker rm zuul-cms
docker create --name zuul-cms --net=tb-net --ip=172.18.0.3 zuul-dms

docker container stop discovery-cms
docker rm discovery-cms
docker create --name discovery-cms --net=tb-net --ip=172.18.0.2 discovery-dms

docker create --name account-db --net=host -p 5432:9081 postgres
docker container stop account-cms
docker rm account-cms
docker create --name account-cms -p 8080:8080 account-dms

docker create --name authentication-db --net=host -p 5432:9081 postgres
docker container stop authentication-cms
docker rm authentication-cms
docker create --name authentication-cms authentication-dms

docker create --name transaction-db --net=host -p 5432:9081 postgres
docker container stop transaction-cms
docker rm transaction-cms
docker create --name transaction-cms transaction-dms

docker create --name person-db --net=host -p 5432:9081 postgres
docker container stop person-cms
docker rm person-cms
docker create --name person-cms person-dms