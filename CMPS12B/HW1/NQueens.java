//Juan Ayala
//jumaayal
//Finds combination of queens placement without attacking each other
import java.io.*;
import java.util.Scanner;

public class NQueens{
  static int size;
  static int intRow;
  static int intCol;
  static int board[][];
  static FileWriter outPut;
  static String solution = "";
  static String printing="";
  static String inFile;
  static String outFile;
  public static void main(String[] args) {
    try{
      inFile= args[0];
      outFile =args[1];
    }catch(IllegalArgumentException e1){
      System.out.println("Invalid Input");
    }
    try{
      Scanner input = new Scanner(new File(inFile));
      FileWriter outPut = new FileWriter(outFile);

      //Keep taking inputs until there is no int tokens
      while(input.hasNextInt()){
        size =input.nextInt();
        intCol=(input.nextInt())-1; // Subtracting 1 to represent array index
        intRow=(input.nextInt())-1;
        board=new int[size][size];
        board[intRow][intCol]=1;

        if(findCombination(0)){
          printing= printing +solution +"\n";
          solution ="";
        }else{
          printing = printing +"No solution" +"\n";
        }
      }

      //Prints to Output.txt
      outPut.write(printing);
      outPut.close();

    }catch(IOException ex){
      System.out.println("Could not create file");
    }
  }

//Finds the placement of Queens to
  public static boolean findCombination(int column){
    //Once the last queen has been placed on the last column return true
    if(column>=(size)){
      return true;
    }
    for(int i=0;i<size;i++){
      //If not being attacked or in column with set queen then do things or else return false
      if(!isUnderAttack(i,column) || column==intCol){
        //Only want to place a queen if it is not in the column where there is a set placement
        if(column!=intCol){
          setQueen(i,column);
        }
        //Goes to next columns to look for combination
        //If no solution is found with other columns it returns false, returning to pre existing situation
        if(findCombination(column+1)){
          //Printing takes into account of the preset queen
          if(column!=intCol){
            solution = (column+1) +" " +(i+1)+" "+ solution;
          }else{
            solution = (intCol+1)+" " +(intRow+1)+" "+ solution;
          }
          return true;

        }else{
          //Comes here if the next queen couldn't be placed
          //Removes placed queen if it is not preset queen
          if(column!=intCol){
            removeQueen(i,column);
          }else{
            return false;
          }

        }
      }
    }
    //Returns false if couldn't find a possible placement of queen in column
    return false;
  }
//Places a 0 in the array to show no placement
  public static void removeQueen(int row,int column){
    board[row][column]=0;
  }

//Places queen in array index
  public static void setQueen(int row,int column){
    board[row][column]=1;
  }
//Using all other functions. This checks if an other queen is attacking at position
  public static boolean isUnderAttack(int row,int column){
    if( checkRow(row) || checkDiagUpLeft(row,column) || checkDiagUpRight(row,column) || checkDiagDownLeft(row,column) || checkDiagDownRight(row,column)){
      return true;
    }else{
      return false;
    }
  }

//Checks if there is a queen in the row
  public static boolean checkRow(int row){
    for(int i =0; i< size; i++){
      if(board[row][i]==1){
        return true;
      }
    }
    return false;
  }

//Checks the diagnol going up to the left range for queens. Returns true if queen is found
  public static boolean checkDiagUpLeft(int row, int column){
    if ((column<=0)||(row<=0)){
      return false;
    }else{
      if(board[row-1][column-1]==1){
        return true;
      }else{
        return checkDiagUpLeft(row-1,column-1);
      }
    }
  }

//Checks the diagnol going down to the left range for queens. Returns true if queen is found
  public static boolean checkDiagDownLeft(int row, int column){
    if ((column<=0)||(row>=(size-1))){
      return false;
    }else{
      if(board[row+1][column-1]==1){
        return true;
      }else{
        return checkDiagDownLeft(row+1,column-1);
      }
    }
  }

//Checks the diagnol going up to the right range for queens. Returns true if queen is found
  public static boolean checkDiagUpRight(int row, int column){
    if ((column>=(size-1))||(row<=0)){
      return false;
    }else{
      if(board[row-1][column+1]==1){
        return true;
      }else{
        return checkDiagUpRight(row-1,column+1);
      }
    }
  }

  //Checks the diagnol going down to the right range for queens. Returns true if queen is found
  public static boolean checkDiagDownRight(int row, int column){
    if ((column>=(size-1))||(row>=(size-1))){
      return false;
    }else{
      if(board[row+1][column+1]==1){
        return true;
      }else{
        return checkDiagDownRight(row+1,column+1);
      }
    }
  }

}
