#Makefile
#Juan Ayala
#jumaayal
#A simple Makefile
Bard: Bard.class LinkedList.class Node.class
	echo Main-class: Bard > Manifest
	jar cvfm Bard.jar Manifest Bard.class LinkedList.class Node.class
	rm Manifest

Bard.class: Bard.java
	javac -Xlint Bard.java

clean:
	rm -f Bard.jar Bard.class LinkedList.class Node.class
