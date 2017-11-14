/*
*balanced.c
*Juan Ayala
*jumaayal
*Uses array based stack to see if text is balanced
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE 1
#define FALSE 0
typedef struct{
  int top;
  int max;
  char brackets[80];
}stack;
char peek(stack * list);
void push(stack * list,char symbol);
char pop(stack * list);
int isEmpty(stack * list);
void popAll(stack *list);
char otherBracket(char item);
int main(int argc, char* argv[]){
  FILE* in; /*file handle for input*/
  FILE* out;/*file handle for output*/
  char input[80];


  /* check command line for correct number of arguments*/
  if(argc != 3){
    printf("usage: %s <input file> <output file>\n",argv[0]);
    exit(EXIT_FAILURE);
  }

  /* open input file for reading*/
  in = fopen(argv[1], "r");
  if( in==NULL ){
    printf("Unable to read from file %s\n", argv[1]);
    exit(EXIT_FAILURE);
  }

  /* open output file for writing */
  out = fopen(argv[2], "w");
  if( out==NULL ){
    printf("Unable to write to file %s\n", argv[2]);
    exit(EXIT_FAILURE);
  }
  /*Setting up the stack*/
  stack load;
  load.max=80;
  load.top=-1;

  /*Will continue running till there is no input*/
  while(fgets(input,80,in)!=NULL){
    int inLength= strlen(input);
    int i;

    //Runs in a loop until last character
    for(i=0;i<inLength;i++){

      //If it is an opening character push into stack
      if(input[i]== '(' || input[i]== '[' || input[i]== '<' || input[i]=='{'){
        push(&load, input[i]);
      }

      /*
      *if it is a closing bracket, check to see if stack has corresponding opening bracket
      *Pop if stack has corresponding brackets
      *Push if not
      */
      if(input[i]== ')' || input[i]== ']' || input[i]== '>' || input[i]=='}'){
        char other = otherBracket(input[i]);
        if(!isEmpty(&load) && peek(&load)==other){
          pop(&load);
        }else{
          push(&load, input[i]);
        }
      }
    }

    /*If there is nothing in stack then it is balanced*/
    if(!isEmpty(&load)){
      fprintf(out, "N\n");
    }else{
      fprintf(out, "Y\n");
    }
    popAll(&load); //pop all so stack can be reused
  }
  fclose(in);
  fclose(out);
  return EXIT_SUCCESS;
}

//adds item to next array index and changes top to be new index
void push(stack * list,char symbol){
  list->top++;
  list->brackets[list->top]=symbol;
}

//Returns the popped item and lowers top index
char pop(stack * list){
  char holder = (list->top);
  list->top--;
  return holder;
}

//Checks if top is at -1 meaning it is empty
int isEmpty(stack * list){
  if ((*list).top <= -1){
    return TRUE;
  }else{
    return FALSE;
  }
}

//Changes all values to zero from top and lowers top to empty
void popAll(stack *list){
  int i;
  for(i=0; i<=(list->max);i++){
    list->brackets[i] =0;
  }
  list->top = -1;
}

//Returns the item at the top
char peek(stack * list){
  return list->brackets[list->top];
}

//Returns what the opening bracket should be compared to closing
char otherBracket(char item){
  if(item==')'){
    return '(';
  }
  if(item==']'){
    return '[';
  }
  if(item=='>'){
    return '<';
  }
  if(item=='}'){
    return '{';
  }
  return '0';
}
