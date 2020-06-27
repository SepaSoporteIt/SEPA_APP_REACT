# SEPA APP

- Dependencias Mysql, maven.

Iniciar app en desarrollo:
mvn spring-boot:run

Iniciar app en produccion:
mvn spring-boot:run -Pprod

Levantar DB en desarrollo:
docker pull mysql
docker run -p 3306:3306 --name mysqlSepa -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=sepa_desarrollo mysql -d
