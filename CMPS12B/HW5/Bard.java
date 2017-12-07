//Juan Ayala
//jumaayal
import java.util.*;
import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
class Bard{
  public static void main(String[] args)throws IOException {
    // open files
    Scanner in = new Scanner(new File("input.txt"));
    Scanner text = new Scanner(new File("Shakespeare.txt"));
    PrintWriter out = new PrintWriter(new FileWriter("analysis.txt"));
    String word;
    int wLength;
    int code;
    LinkedList info;
    //Creats the table where info will be
    HashMap<Integer, LinkedList> table = new HashMap<Integer, LinkedList>();
    while(text.hasNext()){
      word = text.next();
      wLength=word.length();
      code=  word.hashCode();
      if(table.containsKey(code)){
        info=table.get(code);
        if(info.addRep(word)){
          info.addNode(word,wLength);
        }
      }else{
        table.put(code,new LinkedList(new Node(word,wLength,1)));
      }
    }
    LinkedList [] list= table.values().toArray(new LinkedList [0]);
    LinkedList SortedList= new LinkedList();
    int lengthOfList= list.length;
    for(int i=0; i< lengthOfList;i++){
      Node value = list[i].getNode();
      if(!(value.getNext()==null)){
        Node curr = value;
        do{
          SortedList.placeString(new Node( curr.getWord(),curr.getLength(),curr.getRepeat() ));
          curr=curr.getNext();
        }while(!(curr.getNext()==null));
      }else{
        SortedList.placeString(value);
      }
    }
    Node head= SortedList.getNode();
    Node curre=head;
    for(int i=0;i<10;i++){
      curre=curre.getNext();
    }
  }
}
class LinkedList{
  Node start;
  int amount=0;
  LinkedList(Node insert){
    start= insert;
  }
  LinkedList(){

  }

  void addNode(String w, int l){
    Node in= new Node(w,l,1);
    Node holder;
    holder=start;
    start=in;
    start.putNext(holder);
    amount=amount+1;
  }
  boolean addRep(String w){
    if(start.getNext()==null){
      start.repeat();
      return false;
    }
    Node current = start;
    do{
      if(current.getWord().equals(w)){
        current.repeat();
        return false;
      }
      current=current.getNext();
    }while(!(current.getNext()==null));
    return true;
  }



  boolean noCollision(){
    if(start.getNext()==null){
      return true;
    }
    return false;
  }

  Node getNode(){
    return start;
  }
//Places node to sorted order in linked list
  void placeString(Node info){

    Node holder;
    int wordLength= info.getLength();
    int rep = info.getRepeat(); //repetitions
    if(amount==0){
      start=info;
      amount++;
      return;
    }
    if(amount==1){
      if(wordLength<start.getLength()){
        holder=start;
        start=info;
        start.putNext(holder);
        amount++;
        return;
      }else if(wordLength< start.getLength()){
        start.putNext(info);
        amount++;
        return;
      }else if(rep< start.getRepeat()){
        holder=start;
        start=info;
        start.putNext(holder);
        amount++;
        return;
      }else if(rep>start.getRepeat()){
        start.putNext(info);
        amount++;
        return;
      }
    }
    int lol=0;
    int low=0;
    int high=amount;
    int mid=(amount/2);
    while(true){
      lol++;
      if(lol==20){
        System.exit(0);
      }
      Node current = start;
      for(int i=0; i<mid;i++){
        current=current.getNext();
      }
      if(wordLength< current.getLength() && mid==0){
        holder=start;
        start=info;
        start.putNext(holder);
        amount=amount+1;
        return;
      }else if(wordLength> current.getLength() && wordLength==current.getNext().getLength() && rep< current.getNext().getRepeat()){
        holder=current;
        current=info;
        current.putNext(holder);
        amount=amount+1;
        return;
      }else if(wordLength==current.getLength()){
        if(rep>=current.getRepeat()){
          while(current.getNext().getLength()==wordLength && rep>current.getRepeat()){
            current=current.getNext();
          }
          holder=current;
          current=info;
          current.putNext(holder);
          amount=amount+1;
          return;
        }else if(mid==0){
          holder=start;
          start=info;
          start.putNext(holder);
          amount=amount+1;
          return;
        }else{
          if(!(current.getNext()==null)){
            if(wordLength==current.getNext().getLength() && rep<current.getNext().getRepeat()){
              holder=current;
              current=info;
              current.putNext(holder);
              amount=amount+1;
              return;
            }
          }
          high=mid;
          mid=(low+ ((mid-low)/2));
        }
      }else if(wordLength> current.getLength()){
        if(!(current.getNext()==null)&&wordLength<current.getNext().getLength()){
          holder=current;
          current=info;
          current.putNext(holder);
          amount=amount+1;
          return;
        }
        low=mid;
        mid=(mid+(high-mid)/2);

      }else if(wordLength<current.getLength()){
        high=mid;
        mid=(low+((mid-low)/2));

      }else if(mid==amount){
        holder=current;
        current=info;
        current.putNext(holder);
        amount=amount+1;
        return;
      }else{
        holder=current;
        current=info;
        current.putNext(holder);
        amount=amount+1;
        return;
      }
    }
  }

}

class Node{
  Node next;
  String word;
  int length;
  int repeat=0;

  //holds a string and Node
  //Takes an item and puts it in a node
  Node(String newItem, int len, int it){
    word=newItem;
    length=len;
    repeat =it+ repeat;
    next=null;
  }

  //Places a node and string into current node
  Node(String newItem,int len, int it, Node nextNode){
    word = newItem;
    length=len;
    repeat=it;
    next=nextNode;
  }

  //places a string in current node
  void putWord(String newItem){
    word=newItem;
  }
  //Returns item in current Node
  String getWord(){
    return word;
  }

  void putLength(int len){
    length=len;
  }

  int getLength(){
    return length;
  }

  void repeat(){
    repeat++;
  }

  int getRepeat(){
    return repeat;
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
