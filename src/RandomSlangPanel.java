import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * PACKAGE_NAME
 * Created by PC
 * Date 11/29/2022 - 10:49 AM
 * Description: ...
 */
public class RandomSlangPanel extends JPanel implements ActionListener {
    private SlangDictionary slangDictionary;
    private JLabel slangs;
    private JLabel meaning;
    public RandomSlangPanel(SlangDictionary slangDic) {
        slangDictionary = slangDic;
        Font font = new Font("Verdana", Font.BOLD, 50);
        Font font1 = new Font("Verdana", Font.ITALIC, 35);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel btnPnl = new JPanel(new FlowLayout());
        JButton btn = new JButton("Generate a new slang word");
        btnPnl.add(btn);
        btn.addActionListener(this);
        btnPnl.setPreferredSize(new Dimension(300, 10));


        JPanel slangPnl = new JPanel(new FlowLayout());
        JLabel slangLabel = new JLabel("Word");
        slangLabel.setFont(font);
        slangLabel.setForeground(Color.BLUE);
        slangPnl.add(slangLabel);

        JPanel slangsPnl = new JPanel(new FlowLayout());
        slangs = new JLabel("");
        slangs.setFont(font1);
        slangsPnl.add(slangs);

        JPanel meaningPnl = new JPanel(new FlowLayout());
        JLabel meaningLabel = new JLabel("Meaning");
        meaningLabel.setFont(font);
        meaningLabel.setForeground(Color.gray);

        meaningPnl.add(meaningLabel);

        JPanel mPnl = new JPanel(new FlowLayout());
        meaning = new JLabel("");
        meaning.setFont(font1);
        mPnl.add(meaning);

        add(Box.createVerticalStrut(25));

        add(btnPnl);
        add(slangPnl);
        add(slangsPnl);

        add(meaningPnl);
        add(mPnl);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Generate a new slang word")) {
            String[] randomSlang = slangDictionary.randomSlangWord();

            slangs.setText(randomSlang[0]);
            meaning.setText(randomSlang[1]);
        }
    }
}
