import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.Random;
import java.util.Stack;

public class Minesweeper {

    static PrintWriter out = new PrintWriter(System.out);
    static int all_cells = 81;
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();
    static int[] dmx = new int[]{-1, -1, -1, 1, 1, 1, 0, 0};
    static int[] dmy = new int[]{1, 0, -1, 1, 0, -1, -1, 1};
    static char[][] dashboard = new char[9][9];
    static int[][] behind_dash = new int[9][9];

    public static void printall() {
        for (int a = 0; a < 9; ++a) {
            for (int s = 0; s < 9; ++s) {
                System.out.print(dashboard[a][s]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }

    }

    public static void dfs(int dx, int dy) {
        Stack<pair> st = new Stack<>();
        --all_cells;
        pair now = new pair(dx - 1, dy - 1);
        st.push(now);
        while (!st.empty()) {

            for (int a = 0; a < 8; ++a) {

                int x = dmx[a] + dx, y = dmy[a] + dy;
                if (x < 0 || y < 0 || x > 8 || y > 8) continue;
                if (dashboard[x][y] == ' ') continue;
                else if (behind_dash[x][y] >= 1) {
                    --all_cells;
                    dashboard[x][y] = (char) (behind_dash[x][y]);
                    continue;
                }
                dashboard[x][y] = ' ';
                --all_cells;
                st.push(new pair(x, y));
            }
        }
    }

    public static void main(String[] args) {

        for (int a = 0; a < 9; ++a) {
            for (int s = 0; s < 9; ++s) {
                dashboard[a][s] = 'X';
                behind_dash[a][s] = 0;
            }
        }


        System.out.println("please enter two dimensional\n***********************");
        int dx = sc.nextInt();
        int dy = sc.nextInt();


        int randomNumber = random.nextInt(4);
        // generate random number to the position user select

        behind_dash[dx - 1][dy - 1] = randomNumber;
        dashboard[dx - 1][dy - 1] = (char) (behind_dash[dx - 1][dy - 1] + '0');
        printall();

        //to ensure we didn
        behind_dash[dx - 1][dy - 1] = -1;

        dashboard[dx - 1][dy - 1] = (char) (randomNumber + '0');
        System.out.println(randomNumber);
        System.out.println(
                dashboard[dx - 1][dy - 1]);
        int number_of_mines = 10 - randomNumber;
        boolean ok = false;
        if (randomNumber == 0) {
            ok = true;
            dx--;
            dy--;
            for (int a = 0; a < 8; ++a) {
                if (dx + dmx[a] >= 0 && dx + dmx[a] < 9 && dy + dmy[a] >= 0 && dy + dmy[a] < 9) {
                    behind_dash[dx + dmx[a]][dy + dmy[a]] = -1; // you can not make mine here
                }
            }
        } else {

            while (randomNumber > 0) {

                for (int a = 0; a < 8 && randomNumber > 0; ++a) {
                    boolean take = random.nextBoolean();

                    if (take && dx + dmx[a] >= 0 && dx + dmx[a] < 9 && dy + dmy[a] >= 0 && dy + dmy[a] < 9) {
                        if (behind_dash[dx + dmx[a]][dy + dmy[a]] != -2
                        ) {
                            behind_dash[dx + dmx[a]][dy + dmy[a]] = -2; // there is mine here

                            --randomNumber;
                        }
                    }
                }
            }
        }

        while (number_of_mines > 0) {

            int x = random.nextInt(9);
            int y = random.nextInt(9);

            if (behind_dash[x][y] != -1 && behind_dash[x][y] != -2) {
                behind_dash[x][y] = -2;// mine here
                --number_of_mines;
            }
        }

        // make numbers of mines around each cell
        for (int a = 0; a < 9; ++a) {
            for (int s = 0; s < 9; ++s) {
                for (int dim = 0; dim < 8; ++dim) {

                    int x = a + dmx[dim], y = s + dmy[dim];
                    if (x < 0 || y < 0
                            || x >= 9 || y >= 9 ||
                            behind_dash[a][s] <= -1) {
                        continue;
                    }
                    if (behind_dash[x][y] == -2) {
                        behind_dash[a][s]++;
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


            int operation = sc.nextInt();


            if (operation == 1) {

                if (behind_dash[dx - 1][dy - 1] == -2) {

                    System.out.println("you are lose");
                    break;
                } else if (behind_dash[dx - 1][dy - 1] == 0) {

                    dashboard[dx - 1][dy - 1] = ' ';
                    dfs(dx - 1, dy - 1);

                } else {

                    --all_cells;
                    dashboard[dx - 1][dy - 1] = (char) ('0' + dashboard[dx - 1][dy - 1]);
                }

            } else if (operation == 2) {

                ++flag;
                dashboard[dx - 1][dy - 1] = '?';
                if (flag == all_cells) {
                    System.out.println("you win the game now flag equel mines");
                    break;
                }
            } else {
                flag--;
                dashboard[dx - 1][dy - 1] = 'X';
            }
            printall();


        }
        printall();

    }
}
