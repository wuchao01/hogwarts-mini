nohub java -Xms256m -Xms512m -XX:PermSize=64m -XX:MaxPermSize=128m -server -Dserver.port=8087 -jar demo-0.0.1-SNAPSHOT.jar 'hogwarts-mini' --spring.profiles.active=dev >>.//Users/wuchao/Desktop/1.log 2>&1 &