echo -e "------------------------------------"
echo -e "./mvnw package -Pnative -Dquarkus.native.container-build=true"
echo -e "------------------------------------"
./mvnw package -Pnative -Dquarkus.native.container-build=true -DskipTests
echo -e "------------------------------------"
echo -e "docker build -f src/main/docker/Dockerfile.native -t quarkus-open-telemetry ."
docker build -f src/main/docker/Dockerfile.native -t quarkus-open-telemetry .
echo -e "------------------------------------"



