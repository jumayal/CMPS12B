#Makefile
#Juan Ayala
#jumaayal
# Makefile for balanced.c
balanced : balanced.o
	gcc -o balanced balanced.o
balanced.o : balanced.c
	gcc -c -std=c99 -Wall balanced.c
clean :
	rm -f balanced balanced.o
