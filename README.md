# matrix
neo4j with spring data , deploy with docker compose or docker stack

# Important

Edit .env file and put your HOST IP (ifconfig)

Run with docker compose:
Start Database: ./run_compose.sh
Build and run friend microservice from your dev IDE

Access: http://localhost:9000/index.jsf # your host IP

Neo4J: http://localhost:7474/browser/

What is working:
User register, login, avatar update, send user data to neo4j, avatar update

![alt text](https://github.com/armdev/matrix/blob/main/images/socnet.PNG)