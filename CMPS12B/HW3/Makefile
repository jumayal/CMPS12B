#Makefile
#Juan Ayala
#jumaayal
#A simple Makefile
Balanced: Balanced.class StackLs.class Node.class
	echo Main-class: Balanced > Manifest
	jar cvfm Balanced.jar Manifest Balanced.class StackLs.class Node.class
	rm Manifest

Balanced.class: Balanced.java
	javac -Xlint Balanced.java

clean:
	rm -f Balanced.jar Balanced.class StackLs.class Node.class
