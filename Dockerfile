# Define a imagem base
FROM openjdk:17

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiando o arquivo JAR da aplicação para o diretório de trabalho
COPY target/payment-transfer-service-0.0.1-SNAPSHOT.jar ./payment-transfer-service.jar

# Comando a ser executado quando o contêiner for iniciado
CMD ["java", "-jar", "payment-transfer-service.jar"]
