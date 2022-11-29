import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * PACKAGE_NAME
 * Created by PC
 * Date 11/29/2022 - 9:09 AM
 * Description: ...
 */
public class DeleteSlangDialog extends JDialog implements ActionListener {
    SlangDictionary slangDictionary;
    private JTextField searchField;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    boolean isBtnClicked = false;
    String[][] data;
    String[][] copy;
    public DeleteSlangDialog(SlangDictionary slang) {
        slangDictionary = slang;
        setTitle("Delete a Slang word");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JLabel heading = new JLabel("Select row(s) of word, then press the `Delete` button", JLabel.CENTER);

        searchField = new JTextField("Search (e.g. NSFW, OMW, vamping)");
        searchField.setPreferredSize(new Dimension(230, 30));

        searchField = new JTextField("Search (e.g. NSFW, OMW, vamping)");
        searchField.setPreferredSize(new Dimension(230, 30));

        JPanel searchPn = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(this);
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(this);
        searchPn.add(searchField);
        searchPn.add(searchBtn);
        searchPn.add(refreshBtn);

        String[] columnNames = { "#", "Slang Word", "Meaning" };

        data = slangDictionary.convertToTableData();
        copy = slangDictionary.convertToTableData();

        table = new JTable(new DefaultTableModel(columnNames, 0)) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        defaultTableModel = (DefaultTableModel) table.getModel();

        addAllData();
        JScrollPane sp = new JScrollPane(table);
        table.getTableHeader().setReorderingAllowed(false);

        JPanel deletePnl = new JPanel(new FlowLayout());
        JButton dtlBtn = new JButton("Delete");
        deletePnl.add(dtlBtn);
        dtlBtn.addActionListener(this);

        add(Box.createVerticalStrut(20));
        add(heading);
        add(Box.createVerticalStrut(20));
        add(searchPn);
        add(Box.createVerticalStrut(20));
        add(sp);
        add(Box.createVerticalStrut(20));
        add(deletePnl);
    }

    public void showWindow() {
        setVisible(true);
    }

    private void addAllData() {
        for (String[] datum : data) {
            defaultTableModel.addRow(datum);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Delete")) {

            int input = JOptionPane.showConfirmDialog(this, "Do you really want to delete?",
                    "Delete Slang Word", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null);

            System.out.println(input);

            if (input == 0) {
                int[] selection = table.getSelectedRows();
                for (int j : selection) {
                    String slang = (String) defaultTableModel.getValueAt(j, 1);
                    String meaning = (String) defaultTableModel.getValueAt(j, 2);
                    slangDictionary.deleteSlangWord(slang, meaning);
                }
                refreshTable();
                slangDictionary.writeDictionary("slang-edited.txt");
            }
        }
    }

    public void refreshTable() {
        clearTable();
        this.data = slangDictionary.convertToTableData();
        addAllData();
    }

    private void clearTable() {
        int countRow = defaultTableModel.getRowCount();

        for (int i = countRow - 1; i >= 0; i--) {
            defaultTableModel.removeRow(i);
        }
    }
}
