
import java.util.Objects;
import java.util.Scanner;
import java.util.Random;
import java.util.Stack;

public class Minesweeper {


    final static int[] traverse_rows = new int[]{-1, -1, -1, 1, 1, 1, 0, 0};
    final static int[] traverse_columns = new int[]{1, 0, -1, 1, 0, -1, -1, 1};



    private final  Scanner sc ;
    private  int theNumberOfCells ;
    private final int rowSize ;
    private final int columnSize ;

     Cell[][] cell;
    final Random random;
    private int numberOfMines ;


    Minesweeper(int rowSize, int columnSize) {
        random = new Random();
        sc = new Scanner(System.in);
        numberOfMines = 1 + (rowSize * columnSize) / 10;
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        cell = new Cell[rowSize][columnSize];

        theNumberOfCells = columnSize * rowSize;

        for (int a = 0; a < rowSize; ++a) {
            for (int s = 0; s < columnSize; ++s) {
                cell[a][s] = new Cell(a, s);
            }
        }

    }

    public void setTheNumberOfCells(int theNumberOfCells) {
        this.theNumberOfCells = theNumberOfCells;
    }

    public void printMyDashBoard() {
        for (int a = 0; a < rowSize; ++a) {

            for (int s = 0; s < columnSize; ++s) {
                System.out.print(cell[a][s].getShow());
                System.out.print(" ");
            }
            System.out.print("\n");
        }

    }

    public  void dfs(int dx, int dy) {
        Stack<Pair> st = new Stack<>();
        --theNumberOfCells;
        Pair now = new Pair(dx, dy);
        st.push(now);
        while (!st.empty()) {
            now = st.peek();
            dx = now.x;
            dy = now.y;
            st.pop();
            for (int a = 0; a < 8; ++a) {

                int x = traverse_rows[a] + dx, y = traverse_columns[a] + dy;
                if (x < 0 || y < 0 || x >= rowSize || y >= columnSize) continue;
                if (cell[x][y].getShow() == '.') continue;
                else if (cell[x][y].getMineCount() >= 1) {
                    --theNumberOfCells;
                    cell[x][y].setShow((char) ('0' + cell[x][y].getMineCount()));
                    continue;
                } else if (Objects.equals(cell[x][y].getCellStatus(), "Mine")) continue;
                cell[x][y].setShow('.');
                --theNumberOfCells;
                st.push(new Pair(x, y));
            }
        }
    }

    public void start() {


        System.out.println("please enter first cell to open \n***********************");
        int dx = sc.nextInt();
        int dy = sc.nextInt();


        int randomNumber = random.nextInt(4);  // generate random number of mines


        cell[dx - 1][dy - 1].setMineCount(randomNumber);
        cell[dx - 1][dy - 1].setShow((char) (cell[dx - 1][dy - 1].getMineCount() + '0'));
        printMyDashBoard();


        cell[dx - 1][dy - 1].setMineStatus("emptyCell"); //to ensure we didn't make Mine in this position

        cell[dx - 1][dy - 1].setShow((char) ('0' + randomNumber));


        numberOfMines -= randomNumber;
        boolean ok = false;
        dx--;
        dy--;
        if (randomNumber == 0) {
            ok = true;

            for (int a = 0; a < 8; ++a) {
                if (dx + traverse_rows[a] >= 0 && dx + traverse_rows[a] < rowSize
                        && dy + traverse_columns[a] >= 0 && dy + traverse_columns[a] < columnSize) {
                    cell[dx + traverse_rows[a]][dy + traverse_columns[a]].setMineStatus(" emptyCell");
                }
            }
        } else {

            while (randomNumber > 0) {

                for (int a = 0; a < 8 && randomNumber > 0; ++a) {

                    boolean take = random.nextBoolean();

                    if (take && dx + traverse_rows[a] >= 0
                            && dx + traverse_rows[a] < rowSize &&
                            dy + traverse_columns[a] >= 0
                            && dy + traverse_columns[a] < rowSize) {

                        if (!Objects.equals(cell[dx + traverse_rows[a]][dy +
                                traverse_columns[a]].getCellStatus(), "Mine")) {
                            cell[dx + traverse_rows[a]][dy + traverse_columns[a]].setMineStatus("Mine"); // there is mine here

                            --randomNumber;
                        }
                    }
                }
            }
        }

        while (numberOfMines > 0) {

            int x = random.nextInt(rowSize);
            int y = random.nextInt(columnSize);

            if (Objects.equals(cell[x][y].getCellStatus(), "default")) {
                cell[x][y].setMineStatus("Mine");
                --numberOfMines;
            }
        }

        // make numbers of mines around each cell
        for (int a = 0; a < rowSize; ++a) {
            for (int s = 0; s < columnSize; ++s) {
                for (int traverse = 0; traverse < 8; ++traverse) {

                    int x = a + traverse_rows[traverse], y = s + traverse_columns[traverse];
                    if (x < 0 || y < 0
                            || x >= rowSize || y >= columnSize ||
                            Objects.equals(cell[a][s].getCellStatus(), "Mine")) {
                        continue;
                    }
                    if (Objects.equals(cell[x][y].getCellStatus(), "Mine")) {

                        cell[a][s].setMineCount(cell[a][s].getMineCount() + 1);
                    }
                }
            }
        }

// if your chosen cell is empty
        if (ok) {
            dfs(dx, dy);
        }

        System.out.println("""
                your dashboard contains these features\s
                closed cell : 'X'\s
                Open cell that is empty :''\s
                Open cell with a number from 1 to 8 : the number of mines around the cell
                 Cell with a flag : 'F'
                """
        );

        printMyDashBoard();

        int flag = 0;

        System.out.println("select operation you want3\n" +
                "1 open this place\n" +
                "2 flag this place \n" +
                "3 delete flag\n"
        );
        while (true) {


            System.out.println("select dx ,dy");


            dx = sc.nextInt();
            dy = sc.nextInt();


            while (dx <= 0 || dy <= 0 || dx > rowSize || dy > columnSize) {
                System.out.println("enter valid cell");
                dx = sc.nextInt();
                dy = sc.nextInt();
            }

            System.out.println("please enter operation you want 1,2,3");

            int operation = sc.nextInt();
            while (operation <= 0 || operation > 3) {
                System.out.println("please enter valid operation");
                operation = sc.nextInt();
            }

            if (operation == 1) {

                if (cell[dx - 1][dy - 1].getCellStatus() == "Mine") {
                    cell[dx - 1][dy - 1].setShow('M');
                    System.out.println("you are lose");
                    break;
                } else if (cell[dx - 1][dy - 1].getCellStatus() == "emptyCell"
                        || cell[dx - 1][dy - 1].getMineCount() == 0) {

                    cell[dx - 1][dy - 1].setShow('.');
                    dfs(dx - 1, dy - 1);

                } else {

                    --theNumberOfCells;
                    cell[dx - 1][dy - 1].setShow
                            ((char) ('0' + cell[dx - 1][dy - 1].getMineCount()));
                }

            } else if (operation == 2) {

                ++flag;
                cell[dx - 1][dy - 1].setShow('?');
                if (flag == theNumberOfCells) {
                    System.out.println("you win the game now flag equel mines");
                    break;
                }
            } else {
                flag--;
                cell[dx - 1][dy - 1].setShow('X');
            }
            printMyDashBoard();


        }
        printMyDashBoard();


    }
}
