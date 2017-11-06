/*
*Balanced.java
*Juan Ayala
*jumaayal
*This program checks if a string of characters is balanced with brackets
*/
import java.util.*;
import java.io.*;
import java.util.Scanner;
class Balanced extends StackLs{
  public static void main(String[] args)throws IOException {
    if(args.length < 2){
      System.out.println("Usage: java –jar FileCopy.jar <input file> <outputfile>");
      System.exit(1);
    }
    // open files
    Scanner in = new Scanner(new File(args[0]));
    PrintWriter out = new PrintWriter(new FileWriter(args[1]));
    StackLs stack = new StackLs(); //Creates a stack
    //Will continue operating till there is no line of characters
    while(in.hasNextLine()){
      String line = in.nextLine(); //Puts character string into line
      int stringLength= line.length();

      //Goes through each character and if a bracket then it pushes or pops into stack
      for(int i=0; i<stringLength;i++){
        String symbol = Character.toString(line.charAt(i));

      //if it is an opening bracket it will be pushed in
        if(symbol.equals("(") || symbol.equals("[")|| symbol.equals("<")|| symbol.equals("{")){
          stack.push(symbol);
        }
      //If it is the closing bracket it should pop out the opening bracket corresponding to it
        if(symbol.equals("}") || symbol.equals(")")|| symbol.equals(">")||symbol.equals("]")){
          String other = otherBracket(symbol);

        //If it is a closing bracket with no opening bracket then push bracket
          if( !stack.isEmpty() && stack.peek().equals(other)){
            String poppedItem =stack.pop();
          }else{
            stack.push(symbol);
          }
        }
      }
      /*
      *According to method above, if it was ballanced the stack would be empty
      *due to the right amount of open brackets being pushed and popped with
      *corresponding closing brackets
      */
      if(!stack.isEmpty()){
        out.println("N");
      }else{
        out.println("Y");
      }
      stack.emptyOut(); //Emptys out the stack to be used for new string of characters
    }
    in.close();
    out.close();
  }

//This checks whether the next popped item is the opening bracket of the closing bracket
  public static String otherBracket (String item){
    if(item.equals(")")){
      return "(";
    }
    if(item.equals("]")){
      return "[";
    }
    if(item.equals(">")){
      return "<";
    }
    if(item.equals("}")){
      return "{";
    }
    return "";
  }
}

//This class is for the stack methods
class StackLs{
  Node top; //uses the node class or the linked list
  public StackLs(){
    top=null;
  }
  //Checkes whether stack is empty
  public boolean isEmpty(){
    return top == null;
  }
  //Pushes an item to the stack
  public void push(String symbol){
    top = new Node(symbol,top);
  }
  //takes off the recent item makes the next thing the top
  public String pop(){
    if(!isEmpty()){
      Node holder = top;
      top=top.getNext();
      return holder.getItem();
    }else{
      throw new EmptyStackException();
    }
  }
  //Emptys out the stack to create a new stack
  public void emptyOut(){
    top=null;
  }
  //Shows what the next item is without impacting the stack
  public String peek() throws EmptyStackException{
    if(!isEmpty()){
      return top.getItem();
    }else{
      throw new EmptyStackException();
    }
  }
}

//This create a class that can hold a string and its own class
//Used to create a refenced stack
class Node{
  Node next;
  String item;

  //holds a string and Node
  //Takes an item and puts it in a node
  Node(String newItem){
    item=newItem;
    next=null;
  }

  //Places a node and string into current node
  Node(String newItem, Node nextNode){
    item = newItem;
    next=nextNode;
  }

  //places a string in current node
  void putItem(String newItem){
    item=newItem;
  }
  //Returns item in current Node
  String getItem(){
    return item;
  }
  //Places a node in current node
  void putNext(Node newNext){
    next=newNext;
  }
  //Returns the node within the current node
  Node getNext(){
    return next;
  }
}
