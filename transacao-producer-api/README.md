DATASOURCE_PASSWORD=db_password DATASOURCE_URL=jdbc:postgresql://localhost:5433/transacoes_db DATASOURCE_USERNAME=db_user JWT_SECRET=12345678 mvn spring-boot:run 

docker exec -it kafka kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic TRANSACAO-TOPIC


http://localhost:8081/swagger-ui/index.html