import javax.swing.*;
import java.awt.*;

/**
 * PACKAGE_NAME
 * Created by PC
 * Date 11/26/2022 - 1:40 PM
 * Description: ...
 */

public class SearchSlangPanel extends JPanel {
    public SearchSlangPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(400, 600);

        add(Box.createVerticalStrut(40));

        JTextField searchField = new JTextField("Search (e.g. NSFW, OMW, vamping");
        searchField.setPreferredSize(new Dimension(5, 5));
        searchField.setAlignmentX(CENTER_ALIGNMENT);

        JLabel resultLabel = new JLabel("NSFW has ... meaning(s)");
        resultLabel.setAlignmentX(CENTER_ALIGNMENT);

        String[][] data = {
                { "Kundan Kumar Jha", "4031", "CSE" },
                { "Anand Jha", "6014", "IT" }
        };
        String[] columnNames = { "Name", "Roll Number", "Department" };

        JTable table = new JTable(data, columnNames);
        table.setBounds(30, 40, 200, 300);
        table.setEnabled(false);


        add(searchField);
        add(Box.createVerticalStrut(30));
        add(resultLabel);
        add(table);

    }
}
