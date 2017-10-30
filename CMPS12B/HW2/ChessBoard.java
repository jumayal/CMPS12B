//Juan Ayala
//jumaayal
//Reads from files and sees if chess pieces are in right Places
//Also if one exists at a spot and if it attacks
import java.io.*;
import java.util.Scanner;
class ChessBoard{
  static String input;
  static String[] placement;
  static int intRow;
  static int intCol;
  static String Pawn;

//For the list
//Each node has the type of piece and location
  int row;
  int column;
  String piece;
  ChessBoard next;
  ChessBoard(String pawn,int col,int ro){
    piece=pawn;
    row=ro;
    column=col;
    next=null;
  }
  //Enters a new thing in list
  void enterNext(ChessBoard enter){
    next= enter;
  }
  //returns the type of piece through string
  String returnPiece(){
    return piece;
  }

  //Returns the row location
  int returnRow(){
    return row;
  }
  //Returs the Column location
  int returnColumn(){
    return column;
  }
  ChessBoard returnNext(){
    return next;
  }
  public static void main(String[] args)throws IOException {
    if(args.length < 2){
      System.out.println("Usage: java –jar FileCopy.jar <input file> <outputfile>");
      System.exit(1);
    }
    // open files
    Scanner in = new Scanner(new File(args[0]));
    PrintWriter out = new PrintWriter(new FileWriter(args[1]));
    while(in.hasNextLine()){
      //Takes files input an turns them to an array, with the colon got rid of
      input = in.nextLine();
      input = input.replace(":","");
      placement= input.split("\\s");
      int length = placement.length;

      //creates list and first input that is behind the colon
      ChessBoard linkLst = new ChessBoard(placement[2],Integer.valueOf(placement[3]),Integer.parseInt(placement[4]));

      //Query location taken
      intCol= Integer.parseInt(placement[0]);
      intRow=Integer.parseInt(placement[1]);

      //If list is made it finds valid, no existant or attacks
      if(createList(linkLst,placement,length,5)){

        //Checks for only 1 of kings and if there isnt two places on top
        if(!(countPieces(linkLst,"k")==1) || !(countPieces(linkLst,"K")==1) || twoPlaces(linkLst)){
          out.print("Invalid"+"\n");
        }else{

          //Returns a piece if at query or a - if not
          Pawn= exists(linkLst,intCol,intRow);
          if(Pawn.equals("-")){
            out.print(Pawn+"\n");
          }else{
            out.print(Pawn+" ");
            boolean upper;
            if(isUpper(Pawn)){
              upper = true;
            }else{
              upper =false;
            }
            //If the piece attacks any of the other pieces it prints out y.latter n
            if(attack(linkLst,intRow,intCol,upper,Pawn)){
              out.print("y"+"\n");
            }else{
              out.print("n"+"\n");
            }
          }
      }
    }
  }
  in.close();
  out.close();
}

//Places all the information into a list
  public static boolean createList(ChessBoard list, String[] array, int length, int placement){
    if(placement==length){
      return true;
    }
    ChessBoard addition =new ChessBoard(array[placement],Integer.valueOf(array[(placement+1)]),Integer.parseInt(array[(placement+2)]));
    list.enterNext(addition);
    return createList(list.returnNext(),array,length,(placement+3));
  }


//based on the piece, this checks if the piece attacks any of the other pieces
  public static boolean attack(ChessBoard list, int bsrow, int bscolumn, boolean cap, String pawn){
    if(cap==isUpper(list.next.piece)){
      return attack(list.next, bsrow,bscolumn,cap,pawn);
    }
    if(pawn.equalsIgnoreCase("b")){
      return bishop(list.next.row,list.next.column,bsrow,bscolumn);
    }
    if(pawn.equalsIgnoreCase("q")){
      return queen(list.next.row,list.next.column,bsrow,bscolumn);
    }
    if(pawn.equalsIgnoreCase("k")){
      return king(list.next.row,list.next.column,bsrow,bscolumn);
    }
    if(pawn.equalsIgnoreCase("r")){
      return rook(list.next.row,list.next.column,bsrow,bscolumn);
    }
    if(list.next==null){
      return false;
    }
    return attack(list.next,bsrow,bscolumn,cap,pawn);
  }

//Finds if the there is a pawn at the place and returns letter of piece or a -
  public static String exists(ChessBoard list,int column,int row){
    if(list.returnRow() == row && list.returnColumn()==column){
      String pawn=list.returnPiece();
      return pawn;
    }
    if(list.returnNext()==null){
      String notHere="-";
      return notHere;
    }
    return exists(list.returnNext(),column,row);
    }

// Goes checks all pieces if they are on the same spot. Returns true if there is same place
    public static boolean twoPlaces(ChessBoard list){
      if(samePlace(list,list.row,list.column)){
        return true;
      }
      if(list.returnNext()==null){
        return false;
      }
      return twoPlaces(list.returnNext());
    }

//Checks if only one piece has same location
    public static boolean samePlace(ChessBoard list, int row,int col){
      if(list.returnNext()==null){
        return false;
      }
      if(row==list.next.row && col==list.next.column){
        return true;
      }
      return samePlace(list.next,row,col);
    }

//Counts the amount of times a piece exists
    public static int countPieces(ChessBoard list, String pawn){
      if(list.piece.equals(pawn)){
        if(list.returnNext() == null){
          return 1;
        }
        return 1+countPieces(list.next,pawn);
      }
      if(list.returnNext() == null){
        return 0;
      }
      return countPieces(list.next,pawn);
    }

//Checks if the piece is the same color
  public static boolean isUpper(String letter){
    String holder = letter.toUpperCase();
    if(holder.equals(letter)){
      return true;
    }else{
      return false;
    }
  }

//If piece is a bishop range of attack is diagnal.Returns if attacks
  public static boolean bishop(int row, int column, int bsrow, int bscolumn){
    if(row<=bsrow && column<=bscolumn){
      return checkDiagDownLeft(row,column,bsrow,bscolumn);
    }
    if(row>=bsrow && column<=bscolumn){
      return checkDiagUpLeft(row,column,bsrow,bscolumn);
    }
    if(row>=bsrow && column>=bscolumn){
      return checkDiagUpRight(row,column,bsrow,bscolumn);
    }
    if(row<=bsrow && column>=bscolumn){
      return checkDiagDownRight(row,column,bsrow,bscolumn);
    }
    return false;
  }

//If the piece is queen, the range is diagnoal and t. Returns true if attacks
  public static boolean queen(int row, int column, int bsrow, int bscolumn){
    if(row==bsrow || column ==bscolumn){
      return false;
    }
    if(row<=bsrow && column<=bscolumn){
      return checkDiagDownLeft(row,column,bsrow,bscolumn);
    }
    if(row>=bsrow && column<=bscolumn){
      return checkDiagUpLeft(row,column,bsrow,bscolumn);
    }
    if(row>=bsrow && column>=bscolumn){
      return checkDiagUpRight(row,column,bsrow,bscolumn);
    }
    if(row<=bsrow && column>=bscolumn){
      return checkDiagDownRight(row,column,bsrow,bscolumn);
    }
    return false;
  }

//If piece is a king, the range of attack is small box.Returns true if attacks
  public static boolean king(int row, int column, int bsrow, int bscolumn){
    if( (Math.abs(bscolumn-column)<= 1) && (Math.abs(bsrow-row)<=1)){
      return true;
    }else{
      return false;
    }
  }
//If the piece is a rook,the range of attack is a t.Returns true if attacks
  public static boolean rook(int row, int column, int bsrow, int bscolumn){
    if((bscolumn==column) || (bsrow==row)){
      return true;
    }else{
      return false;
    }
  }


//Checks the diagnol going down to the left range. Returns true if pawn is found
  public static boolean checkDiagUpLeft(int row, int column, int bsrow, int bscolumn){
    if ((column<1)||(row>8)){
      return false;
    }else{
      if((row==bsrow) || (column==bscolumn) ){
        return true;
      }else{
        return checkDiagUpLeft(row,column,bsrow+1,bscolumn-1);
      }
    }
  }

//Checks the diagnol going down to the left range. Returns true if pawn is found
  public static boolean checkDiagDownLeft(int row, int column, int bsrow, int bscolumn){
    if ((column<1)||(row<1)){
      return false;
    }else{
      if((row==bsrow) || (column==bscolumn)) {
        return true;
      }else{
        return checkDiagDownLeft(row,column,bsrow-1,bscolumn-1);
      }
    }
  }

//Checks the diagnol going up to the right range. Returns true if pawn is found
  public static boolean checkDiagUpRight(int row, int column, int bsrow, int bscolumn){
    if ((bscolumn>8)||(bsrow>8)){
      return false;
    }else{
      if( (row==bsrow) && (column==bscolumn)){
        return true;
      }else{
        return checkDiagUpRight(row,column,bsrow+1,bscolumn+1);
      }
    }
  }

  //Checks the diagnol going down to the right range. Returns true if pawn is found
  public static boolean checkDiagDownRight(int row, int column, int bsrow, int bscolumn){
    if ((column>8)||(row<1)){
      return false;
    }else{
      if( (row==bsrow) || (column==bscolumn) ){
        return true;
      }else{
        return checkDiagDownRight(row,column,bsrow-1,bscolumn+1);
      }
    }
  }
}
