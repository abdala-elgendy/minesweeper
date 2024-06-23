import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    public static void main(String []args){

        System.out.println("hello in Minesweeper game ");
        System.out.println("please enter the size of the two dimentinal of the game between 1 to 32");
        int row_size=sc.nextInt();
        int column_size=sc.nextInt();

 while(row_size<0||row_size>32||column_size>32||column_size<0){
     System.out.println("please enter valid numbers for row and column size");
     row_size=sc.nextInt();
     column_size=sc.nextInt();
 }
Minesweeper  my_game=new Minesweeper(row_size,column_size);

my_game.start();
    }
}
