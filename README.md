# matrix
neo4j with spring data , deploy with docker compose or docker stack

# Important

Edit .env file and put your HOST IP (ifconfig)

Run with docker compose:
Start Database: ./run_compose.sh
Build and run friend microservice from your dev IDE

Access: http://localhost:9000/index.jsf # your host IP

Neo4J: http://localhost:7474/browser/

What is working?
1. register new account
2. login
3. update profile, upload avatar
4. new accounts page immediately update after changing the avatar

Account data hold in the mongo db, for each account we have reference in the neo4j.


![alt text](https://github.com/armdev/matrix/blob/main/images/socnet.PNG)