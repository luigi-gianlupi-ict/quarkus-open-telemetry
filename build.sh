echo -e "------------------------------------"
echo -e "mvn clean package -DskipTests=true"
echo -e "------------------------------------"
./mvnw package -DskipTests
echo -e "------------------------------------"
echo -e "docker build with par image name = quarkus-open-telemetry"
docker build -f src/main/docker/Dockerfile.jvm -t quarkus-open-telemetry .
echo -e "------------------------------------"



