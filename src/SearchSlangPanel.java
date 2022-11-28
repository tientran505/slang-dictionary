import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
    public SearchSlangPanel(SlangDictionary slangDictionary) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        searchField = new JTextField("Search (e.g. NSFW, OMW, vamping)");
        searchField.setPreferredSize(new Dimension(350, 30));

        searchField.addMouseListener(this);

        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(this);
        JPanel searchPnl = new JPanel();
        searchPnl.setLayout(new FlowLayout(FlowLayout.CENTER, 30 ,0));

        searchPnl.add(searchField);
        searchPnl.add(searchBtn);

        String[][] data = slangDictionary.convertToTableData();

        JLabel resultLabel = new JLabel("Dictionary has " + data.length + " meaning(s)");
        resultLabel.setAlignmentX(CENTER_ALIGNMENT);

        System.out.println(data.length);

        String[] columnNames = { "#", "Slang Word", "Meaning" };

        table = new JTable(data, columnNames);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        JScrollPane sp = new JScrollPane(table);

        table.setEnabled(false);
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
            String slang = searchField.getText();

            if (slang.equals("") || !isEditing) {
                JOptionPane.showMessageDialog(this, "Please Enter slang to find");

                e.getSource();
            }

            isEditing = false;
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
}
