//Mo Putra
import java.util.*;
import java.io.*;

public class SudokuBoard {
   private char[][] board;
   
   public SudokuBoard(String fileName) {
      board = new char[9][9];
      try {
         File file = new File(fileName);
         Scanner sc = new Scanner(file);
         //reads file and puts data into char[][] board
         while (sc.hasNextLine()) {
            for (int r = 0; r < board.length; r++) {
               String line = sc.nextLine();
               for (int c = 0; c < board[r].length; c++) {
                  board[r][c] = line.charAt(c);              
               }
            }
         }
      } catch (FileNotFoundException e) {
         System.out.println("Cannot load: " + fileName);
      }
   }
   
   //checks to see if data is valid
   public boolean isValid() {
      return checkChars() && checkRows() && checkColumns() && checkMini();
   }
   
   //checks to see if the characters of the data are valid
   private boolean checkChars() {
      Set<Character> correctChars = new HashSet<>();
      correctChars.add('.');
      for (int i = 1; i <= 9; i++) {
         //initializing the char values from 1-9 into the HashSet
         correctChars.add((char) (i + 48));
      }
      //iterate through entire board (2d)
      for (int r = 0; r < board.length; r++) {
         for (int c = 0; c < board[r].length; c++) {
            //checks to see if board contains any incorrect data
            if (!correctChars.contains(board[r][c])) {
               return false;
            }
         }
      }
      return true;
   }
   
   //checks to see if rows contain any duplicated values
   private boolean checkRows() {
      for (int r = 0; r < board.length; r++) {
         Set<Character> rowValues = new HashSet<>();
         //iterates through each row of board
         for (int c = 0; c < board[r].length; c++) {
            if (board[r][c] != '.') {
               //checks to see if there are any duplicated values in a row
               if (rowValues.contains(board[r][c])) {
                  return false;
               } else {
                  rowValues.add(board[r][c]);
               }
            }
         }
      }
      return true;
   }
   
   //checks to see if columns contain any duplicated values
   private boolean checkColumns() {
      for (int r = 0; r < board.length; r++) {
         Set<Character> colValues = new HashSet<>();
         for (int c = 0; c < board[r].length; c++) {
            //in this part, I used board[c][r] instead of board[r][c] to find the values in a column
            if (board[c][r] != '.') {
               if (colValues.contains(board[c][r])) {
                  return false;
               } else {
                  colValues.add(board[c][r]);
               }
            }
         }
      }
      return true;
   }
   
   //checks to see if mini squares contain any duplicated values
   private boolean checkMini() {
      for(int i = 1; i <= 9; i++) {
         //grabs each of the 2d 3x3 array from the miniSquare method
         char[][] mini = miniSquare(i);
         Set<Character> miniSet = new HashSet<>();
         //iterates through the mini 2d array and checks if there are any duplicate values
         for (int r = 0; r < mini.length; r++) {
            for (int c = 0; c < mini[r].length; c++) {
               if (mini[r][c] != '.') {
                  if (miniSet.contains(mini[r][c])) {
                     return false;
                  } else {
                     miniSet.add(mini[r][c]);
                  }
               }
            }
         }
      }
      return true;
   }
   
   //creates a 3x3 2d array which represents the mini squares in a sudoku puzzle
   private char[][] miniSquare(int spot) {
      char[][] mini = new char[3][3];
      for (int r = 0; r < 3; r++) {
         for (int c = 0; c < 3; c++) {
            mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
   }
   
   //checks if the puzzle is solved
   public boolean isSolved() {

      //makes sure all the values are valid
      if(!isValid()) return false;
         Map<Character, Integer> characterTracker = new HashMap<>();
         for (char numbers = '1'; numbers <= '9'; numbers++) {
            characterTracker.put(numbers, 0);
         }
         //Map<Character, Integer> characterTracker = new HashMap<>();
         for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
               //if a character exist, then it adds 1 to the counter
               if (characterTracker.containsKey(board[r][c])) {
                  int currentCount = characterTracker.get(board[r][c]);
                  characterTracker.put(board[r][c], currentCount + 1);
               }
            }
         }
         //checks to see if each value associated w/ a key is equal to 9
         int total = 0;
         for (Integer n : characterTracker.values()) {
            total +=n;
         }   
         if (total == 81) return true;
      return false;
   }
   
   public boolean solve() {
      //base cases to check if board is solved and valid
      if(isSolved()) {
         return true;
      }
      if (!isValid()) {
         return false;
      } 
      //iterates through board
      for (int r = 0; r < board.length; r++) {
         for (int c = 0; c < board[r].length; c++) {
            //checks next empty value
            if (board[r][c] == '.') {
               //tries values starting from 1
               for (char num = '1'; num <= '9'; num++) {
                  board[r][c] = num;
                  //recurses through solve() method
                  if (solve()) {
                     return true;
                  }
                  //backtracking
                  board[r][c] = '.';
               }
            }
         }
      }
      return false; 
   }
   
   public String toString() {
      String print = "";
      for (int r = 0; r < board.length; r++) {
         for (int c = 0; c < board[r].length; c++) {
            print += board[r][c] + " ";
         }
         print += "\n";
      }
      return print;
   }
}