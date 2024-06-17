./gradlew clean bootJar
docker compose rm --force
docker image rm webapi
docker volume rm $(docker volume ls -f dangling=true -q)
docker build . -t webapi
docker compose up