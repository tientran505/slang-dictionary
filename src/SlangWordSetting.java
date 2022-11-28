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

        add(addBtn);
        add(editBtn);
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
            AddSlangDialog slangFrame = new AddSlangDialog(slangDictionary);
            slangFrame.setModal(true);
            slangFrame.showWindow();
        }
    }
}