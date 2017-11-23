/*
*SlugFest.java
*Juan Ayala
*jumaayal
*This program describes the arrival and departure times at a teller
*/
import java.util.*;
import java.io.*;
import java.util.Scanner;
class SlugFest{
  public static void main(String[] args)throws IOException {
    if(args.length < 2){
      System.out.println("Usage: java –jar FileCopy.jar <input file> <outputfile>");
      System.exit(1);
    }
    // open files
    Scanner in = new Scanner(new File(args[0]));
    PrintWriter out = new PrintWriter(new FileWriter(args[1]));
    int times=0;
    //Creating the lines
    QueueLs next = new QueueLs();//Holder
    QueueLs lOne = new QueueLs(); //Creates a queue for line One
    QueueLs lTwo = new QueueLs(); //Creates a queue for line Two
    QueueLs lThree= new QueueLs(); // Creates a gueue for line three
    QueueLs lFour= new QueueLs(); // Creates a gueue for line four
    QueueLs lFive= new QueueLs(); // Creates a gueue for line five
    //Will continue operating till there is no line of characters
    while(in.hasNextLine()){
      String line = in.nextLine(); //Puts character string into line
      String[] values = line.split(" ");
      if(values[0].equals("1")){
        lOne.enqueue(Integer.parseInt(values[0]),values[1],Integer.parseInt(values[2]),Integer.parseInt(values[3]));
      } else if(values[0].equals("2")){
        lTwo.enqueue(Integer.parseInt(values[0]),values[1],Integer.parseInt(values[2]),Integer.parseInt(values[3]));
      }else if(values[0].equals("3")){
        lThree.enqueue(Integer.parseInt(values[0]),values[1],Integer.parseInt(values[2]),Integer.parseInt(values[3]));
      }else if(values[0].equals("4")){
        lFour.enqueue(Integer.parseInt(values[0]),values[1],Integer.parseInt(values[2]),Integer.parseInt(values[3]));
      }else if(values[0].equals("5")){
        lFive.enqueue(Integer.parseInt(values[0]),values[1],Integer.parseInt(values[2]),Integer.parseInt(values[3]));
      }
    }
    while(!(lOne.isEmpty() && lTwo.isEmpty() && lThree.isEmpty() && lFour.isEmpty() && lFive.isEmpty()) ){
      next = first(lOne,lTwo,lThree,lFour,lFive);
      Node person = next.dequeue();
      int arrived = person.getArrival();
      if(arrived <= next.getTime()){
        out.println(person.getLine() + " "+ person.getName() + " begins "+ next.getTime());
      }else{
        next.putTime( (arrived-next.getTime()));
        out.println(person.getLine() + " "+ person.getName() + " begins "+ next.getTime());
      }
      next.putTime(person.getTransaction());
  }
  in.close();
  out.close();
}

  static QueueLs first(QueueLs one, QueueLs two, QueueLs three, QueueLs four, QueueLs five){
    QueueLs soon = one;
    QueueLs inLine[] = {two, three, four, five};
    for(int i=0; i<4;i++){
      if(!(soon.isEmpty())&& !(inLine[i].isEmpty())){
        if((soon.getTime() >= inLine[i].getTime() ) && (inLine[i].peek().getArrival() <= soon.getTime())){
          soon = inLine[i];
        }
      }
    }
    return soon;
  }

}
class QueueLs{
  Node lastNode; //uses the node class or the linked list
  int length;
  int time;
  public QueueLs(){
    lastNode=null;
    length=0;
    time=0;
  }
  public int getTime(){
    return time;
  }

  public void putTime(int min){
    time = time+min;
  }
  //Checkes whether stack is empty
  public boolean isEmpty(){
    return lastNode == null;
  }
  //Pushes an item to the stack
  public void enqueue(int line, String id, int arrives, int action){
    Node newNode = new Node(line,id,arrives,action);
    if(isEmpty()){
      newNode.putNext(newNode);
    }else{
      newNode.putNext(lastNode.getNext());
      lastNode.putNext(newNode);
    }
    length++;
    lastNode=newNode;
  }
   public Node dequeue() throws QueueException{
     if(!isEmpty()){
       Node firstNode = lastNode.getNext();
       if(firstNode==lastNode){
         lastNode=null;
       }else{
         lastNode.putNext(firstNode.getNext());
       }
       length--;
       return firstNode;
     }else{
       throw new QueueException("Queue is Empty");
     }
   }

  //Emptys out the stack to create a new stack
  public void dequeueAll(){
    lastNode=null;
    length=0;
  }
  //Shows what the next item is without impacting the stack
  public Node peek() throws QueueException{
    if(!isEmpty()){
      Node firstNode= lastNode.getNext();
      return firstNode;
    }else{
      throw new QueueException("Queue is Empty");
    }
  }
}

//This create a class that can hold a string and its own class
//Used to create a refenced stack
class Node{
  Node next;
  String name;
  int arrival;
  int trans;
  int line;

  //holds a string, int, int and empty Node
  //Takes an item and puts it in a node
  Node(int arrow,String id, int arrives, int action){
    line=arrow;
    name=id;
    arrival= arrives;
    trans=action;
    next=null;
  }

  //Places a node and string into current node
  Node(int arrow,String id, int arrives, int action, Node nextNode){
    line=arrow;
    name=id;
    arrival= arrives;
    trans=action;
    next=nextNode;
  }
  public int getLine(){
    return line;
  }
  //places a string in current node
  void putName(String id){
    name=id;
  }

  void putArrival(int arrives){
    arrival=arrives;
  }

  void putTransaction(int action){
    trans=action;
  }

  //Returns item in current Node
  String getName(){
    return name;
  }

  int getArrival(){
    return arrival;
  }

  int getTransaction(){
    return trans;
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

class QueueException extends RuntimeException{
  public QueueException (String s){
    super(s);
  }
}
