import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Dictionary;

/**
 * PACKAGE_NAME
 * Created by PC
 * Date 11/29/2022 - 4:22 AM
 * Description: ...
 */
public class EditSlangDialog extends JDialog implements TableModelListener, ActionListener {
    private SlangDictionary slangDictionary;
    private JTextField searchField;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    boolean isBtnClicked = false;
    String[][] data;
    String[][] copy;

    public EditSlangDialog(SlangDictionary slang) {
        slangDictionary = slang;
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Pick a cell to edit, then press enter", JLabel.CENTER);

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

        table = new JTable(new DefaultTableModel(columnNames, 0));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        defaultTableModel = (DefaultTableModel) table.getModel();

        JScrollPane tableScroll = new JScrollPane(table);
        addAllData();
        JScrollPane sp = new JScrollPane(table);
        table.getTableHeader().setReorderingAllowed(false);
        table.getModel().addTableModelListener(this);
        table.

        add(Box.createVerticalStrut(20));
        add(heading);
        add(Box.createVerticalStrut(20));
        add(searchPn);
        add(Box.createVerticalStrut(20));
        add(sp);

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
    public void tableChanged(TableModelEvent e) {
        if (isBtnClicked) return;
        int rSelectedCell = table.getSelectedRow();
        int cSelectedCell = table.getSelectedColumn();

        String data = (String) table.getValueAt(rSelectedCell, cSelectedCell);
        String oldKey = copy[rSelectedCell][1];
        String oldValue = copy[rSelectedCell][2];

        if (cSelectedCell == 1) {
            System.out.println(slangDictionary.editSlang(oldKey, data));
        }
        else if (cSelectedCell == 2) {
            slangDictionary.overwriteSlang(oldKey, oldValue, data);
        }

        copy[rSelectedCell][cSelectedCell] = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        isBtnClicked = true;
        String cmd = e.getActionCommand();
        if (cmd.equals("Search")) {
            String slang = searchField.getText();

            if (slang.equals("")) {
                JOptionPane.showMessageDialog(this, "Please Enter slang to find");
            }
            else {
                String[][] newDatas = this.slangDictionary.searchBySlangWord(slang.toUpperCase());

                if (newDatas != null) {
                    clearTable();

                    for (String[] newData : newDatas) {
                        defaultTableModel.addRow(newData);
                    }
                }
            }
        }
        else if (cmd.equals("Refresh")) {
            clearTable();
            addAllData();
        }
    }

    private void clearTable() {
        int countRow = defaultTableModel.getRowCount();

        for (int i = countRow - 1; i >= 0; i--) {
            defaultTableModel.removeRow(i);
        }
    }
}
