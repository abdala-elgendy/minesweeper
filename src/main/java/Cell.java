public class Cell {

    int row,column;
    char show;
    int current_value;
  Cell(int row ,int column){

      this.row=row;

      this.column=column;

      show='X';

      current_value=0; // -2 -> mine    -1 -> empty  cant make mine in it
                      // else the number of mines around this cell
  }
}
