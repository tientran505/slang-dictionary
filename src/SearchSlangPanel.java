import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

/**
 * PACKAGE_NAME
 * Created by PC
 * Date 11/26/2022 - 1:40 PM
 * Description: ...
 */

public class SearchSlangPanel extends JPanel implements ActionListener, MouseListener {
    private boolean isEditing = false;
    private JTextField searchField;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private SlangDictionary slangDictionary;
    private String[][] data;

    private JLabel resultLabel;
    JComboBox<String> jComboBox;

    public SearchSlangPanel(SlangDictionary slangDictionary) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.slangDictionary = slangDictionary;


        searchField = new JTextField("Search (e.g. NSFW, OMW, vamping)");
        searchField.setPreferredSize(new Dimension(250, 30));

        searchField.addMouseListener(this);

        String[] options = {"Search by slang word", "Search by definition keyword"};

        jComboBox = new JComboBox<>(options);

        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(this);
        JPanel searchPnl = new JPanel();
        searchPnl.setLayout(new FlowLayout(FlowLayout.CENTER, 30 ,0));

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(this);

        searchPnl.add(jComboBox);
        searchPnl.add(searchField);
        searchPnl.add(searchBtn);
        searchPnl.add(refreshBtn);

        data = slangDictionary.convertToTableData();

        resultLabel = new JLabel("Dictionary has " + data.length + " meaning(s)");
        resultLabel.setAlignmentX(CENTER_ALIGNMENT);

        System.out.println(data.length);

        String[] columnNames = { "#", "Slang Word", "Meaning" };

        table = new JTable(new DefaultTableModel(columnNames, 0)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        defaultTableModel = (DefaultTableModel) table.getModel();
        addAllData();
        JScrollPane sp = new JScrollPane(table);

        table.getTableHeader().setReorderingAllowed(false);

        add(Box.createVerticalStrut(20));
        add(searchPnl);
        add(Box.createVerticalStrut(20));
        add(resultLabel);
        add(Box.createVerticalStrut(20));
        add(sp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Search")) {
            String selector = Objects.requireNonNull(jComboBox.getSelectedItem()).toString();
            if (selector.equals("Search by slang word")) {
                String slang = searchField.getText();

                if (slang.equals("")) {
                    JOptionPane.showMessageDialog(this, "Please Enter slang to find");
                }
                else {
                    String[][] newDatas = this.slangDictionary.searchBySlangWord(slang.toUpperCase());

                    if (newDatas != null) {
                        clearTable();

                        resultLabel.setText(slang + " has " + newDatas.length + " meaning(s)");

                        for (String[] newData : newDatas) {
                            defaultTableModel.addRow(newData);
                        }
                    }
                }
            }

            else if (selector.equals("Search by definition keyword")) {
                String keyWord = searchField.getText();

                if (keyWord.equals("")) {
                    JOptionPane.showMessageDialog(this, "Please Enter keyword to find");
                }
                else {
                    String[][] slangs = this.slangDictionary.searchByDefinition(keyWord);

                    if (slangs == null) {

                    }
                    else {
                        clearTable();
                        resultLabel.setText("Slang Dictionary has " + slangs.length
                                + " meaning(s) with keyword `" + keyWord + "`");

                        for (String[] slang: slangs) {
                            defaultTableModel.addRow(slang);
                        }
                    }
                }
            }
        }
        else if (cmd.equals("Refresh")) {
            clearTable();
            addAllData();
            resultLabel.setText("Dictionary has " + data.length + " meaning(s)");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!isEditing) {
            searchField.setText("");
            isEditing = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void clearTable() {
        int countRow = defaultTableModel.getRowCount();

        for (int i = countRow - 1; i >= 0; i--) {
            defaultTableModel.removeRow(i);
        }
    }

    public void addAllData() {
        data = slangDictionary.convertToTableData();
        for (String[] datum : data) {
            defaultTableModel.addRow(datum);
        }
    }
}
