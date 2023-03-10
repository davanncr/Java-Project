package Provider;

import javax.swing.table.DefaultTableModel;
public class TableEditable extends DefaultTableModel {
    private boolean[] editableRows;
    public TableEditable() {
        editableRows = new boolean[100];
    }
    @Override
    public boolean isCellEditable(int row, int column) {
        //return isCellEditable(row, column);
        return editableRows[row];
    }
    public void setRowEditable(int row, boolean editable) {
            if(row!=-1){
                editableRows[row] = editable;
            }
    }

}

