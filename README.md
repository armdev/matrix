# matrix
neo4j with spring data , deploy with docker compose or docker stack

# Important

Edit .env file and put your HOST IP (ifconfig)

1. Run in localhost:
Start Database: ./neo4j_start.sh
Build and run friend microservice from your dev IDE
Access: http://localhost:9090/swagger-ui.html
Database: http://localhost:7474/browser/

2. Run with docker compose:
Start Database: ./run_compose.sh
Build and run friend microservice from your dev IDE
Access: http://192.168.1.7:9090/swagger-ui.html # your host IP
Database: http://192.168.1.7:7474/browser/ # your host ip

2. Run with docker compose:
Start Database: ./run_stack.sh
Build and run friend microservice from your dev IDE
Access: http://192.168.1.7:9090/swagger-ui.html # your host IP
Database: http://192.168.1.7:7474/browser/ # your host ip

