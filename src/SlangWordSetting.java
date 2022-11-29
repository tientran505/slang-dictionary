import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * PACKAGE_NAME
 * Created by PC
 * Date 11/29/2022 - 1:20 AM
 * Description: ...
 */
public class SlangWordSetting extends JPanel implements ActionListener {
    SlangDictionary slangDictionary;

    public SlangWordSetting(SlangDictionary slang) {
        slangDictionary = slang;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton addBtn = new JButton("Add a new Slang word");
        addBtn.addActionListener(this);

        JButton editBtn = new JButton("Edit a slang word");
        editBtn.addActionListener(this);

        JButton dltBtn = new JButton("Delete a slang word");
        dltBtn.addActionListener(this);

        JButton rsBtn = new JButton("Reset Slang Dictionary");
        rsBtn.addActionListener(this);

        add(addBtn);
        add(editBtn);
        add(dltBtn);
        add(rsBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (Objects.equals(cmd, "Add a new Slang word")) {
            AddSlangDialog slangFrame = new AddSlangDialog(slangDictionary);
            slangFrame.setModal(true);
            slangFrame.showWindow();
        }
        else if (Objects.equals(cmd, "Edit a slang word")) {
            EditSlangDialog editSlangDialog = new EditSlangDialog(slangDictionary);
            editSlangDialog.setModal(true);
            editSlangDialog.showWindow();
        }
        else if (Objects.equals(cmd, "Delete a slang word")) {
            DeleteSlangDialog deleteSlangDialog = new DeleteSlangDialog(slangDictionary);
            deleteSlangDialog.setModal(true);
            deleteSlangDialog.showWindow();
        }
        else if (Objects.equals(cmd, "Reset Slang Dictionary")) {
            int input = JOptionPane.showConfirmDialog(this, "Do you really want to reset" +
                            " dictionary? This action can't be reversed!",
                    "Reset Slang Dictionary", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null);

            if (input == 0) {
                slangDictionary.clearDictionary();
                slangDictionary.importDictionary("slang.txt");
                JOptionPane.showMessageDialog(this, "Slang dictionary has been " +
                                "successfully reset", "Message Box",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
