#Juan Ayala
#jumaayal
#A simple Makefile
ChessBoard: ChessBoard.class
	echo Main-class: ChessBoard > Manifest
	jar cvfm ChessBoard.jar Manifest ChessBoard.class
	rm Manifest

ChessBoard.class: ChessBoard.java
	javac -Xlint ChessBoard.java

clean:
	rm -f ChessBoard.jar ChessBoard.class
