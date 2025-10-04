# wishlist-ecomm
API for wishlist management


Para rodar a aplicação 

Via IDE

atualize a variavel de ambiente
spring.profiles.active=local

suba os containeres:
docker-compose up

ou apenas o banco de dados
docker-compose up mongo-db

rode a aplicação pela sua IDE

ou pelo comando
mvn spring-boot:run

para parar a aplicação use o comando:
mvn spring-boot:stop 

ou a interface da sua IDE

Via imagem docker

Verifique se os arquivos .jar foram gerados corretamente, fazendo limpeza e construção do projeto:
mvn clean package

rode o comando para executar o build da imagem junto com o banco de dados:
docker-compose up --build