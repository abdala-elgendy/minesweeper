public class Cell {

    private final int row;
    private final int column;
    private char show;
    private int mineCount;
    private String cellStatus;

    Cell(int row, int column) {

        this.row = row;

        this.column = column;

        show = 'X';
        cellStatus= "default";
        mineCount = 0;
    }

    public int getRow() {
        return row;
    }

    public String getCellStatus() {
        return cellStatus;
    }

    public void setMineStatus(String mineStatus) {
        this.cellStatus = mineStatus;
    }

    public int getColumn() {
        return column;
    }

    public char getShow() {
        return show;
    }

    public void setShow(char show) {
        this.show = show;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }
}
