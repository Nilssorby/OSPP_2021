all:
	mkdir -p classes
	javac -d classes frontend/src/*.java
	javac -d classes backend/*.java
	javac -d classes serverClient/*.java

server: all
	java -cp classes serverClient.Server
run: all
	java -cp classes frontend.src.Main

clean:
	rm -rf classes
