package Provider;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class EditableTableExample extends JFrame {

    private JTable table;
    private JButton editButton;
    private MyTableModel tableModel;

    public EditableTableExample() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Create the table model and set it as the model for the table
        tableModel = new MyTableModel();
        table = new JTable(tableModel);
        // Disable cell selection in the table
        table.setCellSelectionEnabled(false);

        // Create the button and add an action listener to it
        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Enable editing of the selected row
                    tableModel.setRowEditable(selectedRow, true);
                    table.setRowSelectionInterval(selectedRow, selectedRow);
                    table.setEnabled(true);
                    // Start editing the first cell of the selected row
                    table.editCellAt(selectedRow, 0);
                    // Set the focus on the cell editor
                    Component editorComponent = table.getEditorComponent();
                    if (editorComponent != null) {
                        editorComponent.requestFocus();
                    }
                }
            }
        });

        // Add the table and button to the frame
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(editButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new EditableTableExample();
    }

    // Custom table model that does not allow editing of any cell
    private class MyTableModel extends DefaultTableModel {

        private boolean[] editableRows;

        public MyTableModel() {
            super(new Object[][]{{"Row 1", "Value 1"}, {"Row 2", "Value 2"}, {"Row 3", "Value 3"}},
                    new Object[]{"Column 1", "Column 2"});
            editableRows = new boolean[getRowCount()];
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return editableRows[row];
        }

        public void setRowEditable(int row, boolean editable) {
            editableRows[row] = editable;
        }
    }
}
