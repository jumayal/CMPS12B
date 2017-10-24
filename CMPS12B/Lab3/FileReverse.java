import java.io.*;
import java.util.Scanner;
class FileReverse{
  static char holder;
  public static void main(String[] args)throws IOException{
    if(args.length < 2){
      System.out.println("Usage: java –jar FileTokens.jar <input file><output file>");
      System.exit(1);
    }
    // open files
    Scanner in = new Scanner(new File(args[0]));
    PrintWriter out = new PrintWriter(new FileWriter(args[1]));
    // read lines from in, extract and print tokens from each line
    while( in.hasNext() ){
      // trim leading and trailing spaces, then add one trailing space so
      // split works on blank lines
      String line = in.next();
      line = stringReverse(line);
      out.println(line);

    }
    // close files
    in.close();
    out.close();
  }
  public static String stringReverse(String s){
    int length= s.length();
    int switching= length/2;
    char [] characters=s.toCharArray();
    for(int i=0; i<switching;i++){
      holder = characters[i];
      characters[i] = characters[(length-1)-i];
      characters[(length-1)-i]=holder;
    }
    String reverse="";
    for(int i=0;i<length;i++){
      reverse=reverse+characters[i];
    }
    return reverse;
  }
}
