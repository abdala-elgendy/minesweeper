public class Cell {

    int row,column;
    char show;
    int mineCount;
  Cell(int row ,int column){

      this.row=row;

      this.column=column;

      show='X';

      mineCount=0; // -2 -> mine    -1 -> empty  cant make mine in it
                      // else the number of mines around this cell
  }
}
