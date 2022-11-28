import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * PACKAGE_NAME
 * Created by PC
 * Date 11/29/2022 - 2:31 AM
 * Description: ...
 */
public class AddSlangDialog extends JDialog implements ActionListener {
    private JTextField slangField;
    private JTextField meaningField;
    SlangDictionary slangDictionary;
    public AddSlangDialog(SlangDictionary slang) {
        slangDictionary = slang;
        setSize(500, 300);
        setTitle("Add Slang");
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("ADD THE NEW SLANG WORD", JLabel.CENTER);

        JPanel slangPnl = new JPanel();
        JLabel slangLb = new JLabel("Slang Word");
        slangLb.setPreferredSize(new Dimension(100, 30));
        slangField = new JTextField();
        slangField.setPreferredSize(new Dimension(200, 30));
        slangPnl.add(slangLb);
        slangPnl.add(slangField);

        JPanel meaningPnl = new JPanel();
        JLabel meaningLb = new JLabel("Meaning");
        meaningLb.setPreferredSize(new Dimension(100, 30));
        meaningField = new JTextField();
        meaningField.setPreferredSize(new Dimension(200, 30));
        meaningPnl.add(meaningLb);
        meaningPnl.add(meaningField);

        JPanel footPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        JButton addBtn = new JButton("Add");
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(this);
        addBtn.addActionListener(this);

        JLabel empt = new JLabel("");
        empt.setPreferredSize(new Dimension(20, 30));

        footPnl.add(addBtn);
        footPnl.add(cancelBtn);
        footPnl.add(empt);

        add(Box.createVerticalStrut(20));
        add(heading);
        add(Box.createVerticalStrut(30));
        add(slangPnl);
        add(meaningPnl);
        add(footPnl);
    }

    public void showWindow() {
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (Objects.equals(cmd, "Cancel")) {
            dispose();
        }
        else if (Objects.equals(cmd, "Add")) {
            String slang = slangField.getText();
            String meaning = meaningField.getText();

            if (slang.equals("") || meaning.equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
            }
            else {
                if (slangDictionary.addSlangWord(slang, meaning)) {
                    JOptionPane.showMessageDialog(this, "Slang word successfully added");
                }
                else {
                    Object[] options = {"Overwrite", "Duplicate", "Cancel"};
                    int n = JOptionPane.showOptionDialog(this, "Entered slang word is existed, do" +
                                    " you want to...",
                            "Something Wrong!",  JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[2]);

                    if (n == 0) {
                        String[][] datas = slangDictionary.searchBySlangWord(slang);
                        ArrayList<JCheckBox> al = new ArrayList<JCheckBox>();
                        for (String[] data : datas) {
                            JCheckBox box = new JCheckBox(data[2]);
                            box.addItemListener(new ItemListener() {
                                @Override
                                public void itemStateChanged(ItemEvent e) {
                                    System.out.println(e.getStateChange() == 1);
                                }
                            });
                            al.add(box);
                        }
                        Object[] objects = (Object[]) al.toArray(new Object[al.size()]);
                        int k = JOptionPane.showConfirmDialog(this, objects);

                        if (k == 0) {
                            for (int i = 0; i < al.size(); i++) {
                                if (al.get(i).isSelected()) {
                                    System.out.println(datas[i][2]);
                                    slangDictionary.overwriteSlang(slang, datas[i][2], meaning);
                                }
                            }

                            slangDictionary.writeDictionary("slang-edited.txt");
                        }

                        System.out.println(k);
                    }
                    else if (n == 1) {
                        slangDictionary.duplicateSlang(slang, meaning);
                    }
                }
            }
        }
    }
}
