
import java.util.Scanner;
import java.util.Random;
import java.util.Stack;

public class Minesweeper {


    static int[] traverse_rows = new int[]{-1, -1, -1, 1, 1, 1, 0, 0};
    static int[] traverse_columns = new int[]{1, 0, -1, 1, 0, -1, -1, 1};
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

  //  static int not_mine_here=1;
    static int Mine=-2;
    static int emptyCell=-1;
    static  int the_number_of_cells =0;
    static  int row_size=33;
    static  int column_size=33;
   static int numberOfMines=0;


 // static  char[][] dashboard = new char[row_size][column_size];
    static Cell[][] cell = new Cell[row_size][column_size];


    Minesweeper(int row_size,int column_size){

        numberOfMines = 1+(row_size* column_size)/ 10;
        Minesweeper.row_size =row_size;
        Minesweeper.column_size =column_size;

        the_number_of_cells=column_size*row_size;

        for(int a=0;a<row_size;++a){
            for(int s=0;s<column_size;++s) {
                cell[a][s]=new Cell(a,s);
            }
            }

    }


    public static void printall() {
        for (int a = 0; a <row_size ; ++a) {
            for (int s = 0; s < column_size; ++s) {
                System.out.print(cell[a][s].show);
                System.out.print(" ");
            }
            System.out.print("\n");
        }

    }

    public static void dfs(int dx, int dy) {
        Stack<pair> st = new Stack<>();
        --the_number_of_cells;
        pair now = new pair(dx , dy );
        st.push(now);
        while (!st.empty()) {
         now=st.peek();
         dx=now.x;
         dy=now.y;
         st.pop();
            for (int a = 0; a < 8; ++a) {

                int x = traverse_rows[a] + dx, y = traverse_columns[a] + dy;
                if (x < 0 || y < 0 || x >= row_size || y >= column_size) continue;
                if (cell[x][y].show == '.') continue;
                else if (cell[x][y].current_value >= 1) {
                    --the_number_of_cells;
                    cell[x][y].show = (char) ('0'+cell[x][y].current_value);
                    continue;
                }
                cell[x][y].show = '.';
                --the_number_of_cells;
                st.push(new pair(x, y));
            }
        }
    }

    public  void start() {

        for (int a = 0; a <row_size; ++a) {
            for (int s = 0; s < column_size; ++s) {
                cell[a][s].show = 'X';
                cell[a][s].current_value = 0;
            }
        }


        System.out.println("please enter first cell to open \n***********************");
        int dx = sc.nextInt();
        int dy = sc.nextInt();


        int randomNumber = random.nextInt(4);  // generate random number of mines


        cell[dx - 1][dy - 1].current_value = randomNumber;
        cell[dx - 1][dy - 1].show = (char) (cell[dx - 1][dy - 1].current_value + '0');
        printall();


       cell[dx - 1][dy - 1].current_value = emptyCell; //to ensure we didn't make Mine in this position

        cell[dx - 1][dy - 1].show = (char) ( '0'+randomNumber );


         numberOfMines -= randomNumber;
        boolean ok = false;
        dx--;
        dy--;
        if (randomNumber == 0) {
            ok = true;

            for (int a = 0; a < 8; ++a) {
                if (dx + traverse_rows[a] >= 0 && dx + traverse_rows[a] < 9
                        && dy + traverse_columns[a] >= 0 && dy + traverse_columns[a] < 9) {
                    cell[dx + traverse_rows[a]][dy + traverse_columns[a]].current_value = emptyCell; // you can not make mine here
                }
            }
        } else {

            while (randomNumber > 0) {

                for (int a = 0; a < 8 && randomNumber > 0; ++a) {

                    boolean take = random.nextBoolean();

                    if (take && dx +traverse_rows[a] >= 0
                            && dx + traverse_rows[a] < row_size &&
                            dy + traverse_columns[a] >= 0
                            && dy + traverse_columns[a] < row_size) {

                        if (cell[dx + traverse_rows[a]][dy +
                                traverse_columns[a]].current_value != Mine)
                         {
                           cell[dx + traverse_rows[a]][dy + traverse_columns[a]].current_value = Mine; // there is mine here

                            --randomNumber;
                        }
                    }
                }
            }
        }

        while (numberOfMines > 0) {

            int x = random.nextInt(row_size);
            int y = random.nextInt(column_size);

            if (cell[x][y].current_value != emptyCell && cell[x][y].current_value != Mine) {
                cell[x][y].current_value = Mine;
                --numberOfMines;
            }
        }

        // make numbers of mines around each cell
        for (int a = 0; a < row_size; ++a) {
            for (int s = 0; s < column_size; ++s) {
                for (int traverse = 0; traverse < 8; ++traverse) {

                    int x = a + traverse_rows[traverse], y = s + traverse_columns[traverse];
                    if (x < 0 || y < 0
                            || x >= row_size || y >= column_size ||
                            cell[a][s].show==Mine) {
                        continue;
                    }
                    if (cell[x][y].current_value == Mine) {
                        if(cell[a][s].current_value==-1)cell[a][s].current_value=0;
                        cell[a][s].current_value++;
                    }
                }
            }
        }

// if your choosed cell is empty
        if (ok) {
            dfs(dx, dy);
        }

        System.out.println("your dashboard contains these features \n" +
                "closed cell : 'X' \n" +
                "Open cell that is empty :'' \n" +
                "Open cell with a number from 1 to 8 : " +
                "the number of mines around the cell\n" +
                " Cell with a flag : 'F'\n"
        );

        printall();

        int flag = 0;

        System.out.println("select operation you want \n" +
                "1 open this place\n" +
                "2 flag this place \n" +
                "3 delete flag\n"
        );
        while (true) {


            System.out.println("select dx ,dy");


            dx = sc.nextInt();
            dy = sc.nextInt();


            while(dx<=0||dy<=0||dx>row_size||dy>column_size){
                System.out.println("enter valid cell");
                dx=sc.nextInt();
                dy=sc.nextInt();
            }

            System.out.println("please enter operation you want 1,2,3");

            int operation = sc.nextInt();
           while(operation<=0||operation>3){
               System.out.println("please enter valid operation");
               operation=sc.nextInt();
           }

            if (operation == 1) {

                if (cell[dx - 1][dy - 1].current_value == Mine) {

                    System.out.println("you are lose");
                    break;
                } else if (cell[dx - 1][dy - 1].current_value == emptyCell
                        ||cell[dx-1][dy-1].current_value==0) {

                    cell[dx - 1][dy - 1].show = '.';
                    dfs(dx - 1, dy - 1);

                } else {

                    --the_number_of_cells;
                    cell[dx - 1][dy - 1].show = (char) ('0' + cell[dx - 1][dy - 1].current_value);
                }

            } else if (operation == 2) {

                ++flag;
                cell[dx - 1][dy - 1].show = '?';
                if (flag == the_number_of_cells) {
                    System.out.println("you win the game now flag equel mines");
                    break;
                }
            } else {
                flag--;
                cell[dx - 1][dy - 1].show = 'X';
            }
            printall();


        }
        printall();

    }
}
