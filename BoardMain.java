import java.util.*;
import java.io.*;

public class BoardMain {
   public static void main(String[] args) { 
      
      SudokuBoard board = new SudokuBoard("boards/very-fast-solve.sdk");
      
      //checks if board is valid
      if(!board.isValid()) 
         System.out.println("Board is not valid");
      else {
         System.out.println("Board is valid");
      }
      
      //checks if board is already solved
      if(!board.isSolved()) 
         System.out.println("Board is not solved yet");
      else {
         System.out.println("Board is already solved");
      }
      
      System.out.println("\nInitial board");
      System.out.println(board);
      System.out.println();

      //solves board
      System.out.print("Solving board...");
      long start = System.currentTimeMillis();
      board.solve();
      long stop = System.currentTimeMillis();
      
      //shows how long board was solved
      System.out.printf("SOLVED in %.3f seconds.\n", ((stop-start)/1000.0));
      System.out.println();
      System.out.println(board);
   }
}

