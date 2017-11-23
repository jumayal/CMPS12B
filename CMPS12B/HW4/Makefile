#Makefile
#Juan Ayala
#jumaayal
#A simple Makefile for SlugFest
SlugFest: SlugFest.class QueueLs.class Node.class QueueException.class
	echo Main-class: SlugFest > Manifest
	jar cvfm SlugFest.jar Manifest SlugFest.class QueueLs.class Node.class QueueException.class
	rm Manifest

SlugFest.class: SlugFest.java
	javac -Xlint SlugFest.java

clean:
	rm -f SlugFest.jar SlugFest.class QueueLs.class Node.class QueueException.class
