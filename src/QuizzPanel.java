import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * PACKAGE_NAME
 * Created by PC
 * Date 11/29/2022 - 11:38 AM
 * Description: ...
 */
public class QuizzPanel extends JPanel implements ActionListener {
    private SlangDictionary slangDictionary;
    private JComboBox<String> jComboBox;
    private JLabel question;
    private JLabel qContent;
    private JLabel aAns;
    private JLabel bAns;
    private JLabel cAns;
    private JLabel dAns;
    private GridLayout grid;
    private JPanel ansPnl;


    public QuizzPanel(SlangDictionary slangDic) {
        slangDictionary = slangDic;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Border blackline = BorderFactory.createLineBorder(Color.black);

        Font font = new Font("Verdana", Font.BOLD, 35);
        Font fontBtn = new Font("Verdana", Font.BOLD, 25);
        Font qstFont = new Font("Verdana", Font.BOLD, 20);


        String[] options = {"Quiz by slang word", "Quiz by definition"};
        JPanel head = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        head.setBorder(blackline);
        jComboBox = new JComboBox<>(options);
        JButton startBtn = new JButton("Start");
        startBtn.setFont(fontBtn);
        startBtn.addActionListener(this);

        JPanel questionPnl = new JPanel();
        JLabel questionLb = new JLabel("Random Quiz");
        questionPnl.add(questionLb);
        questionLb.setHorizontalAlignment(JLabel.CENTER);
        questionLb.setVerticalAlignment(JLabel.CENTER);
        questionLb.setBorder(blackline);
        questionLb.setFont(font);
        questionLb.setForeground(Color.BLUE);

        head.add(jComboBox);
        head.add(startBtn);

        JPanel qPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        qContent = new JLabel("This is question number #");
        qContent.setFont(qstFont);
        qPnl.add(qContent);

        grid = new GridLayout();
        ansPnl = new JPanel(grid);
        JPanel aPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel a = new JLabel("  A  ");
        a.setBorder(blackline);
        aAns = new JLabel("This is answer A");
        aPnl.add(a);
        aPnl.add(aAns);

        JPanel bPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel b = new JLabel("  B  ");
        bAns = new JLabel("This is answer B");
        b.setBorder(blackline);
        bPnl.add(b);
        bPnl.add(bAns);

        JPanel cPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel c = new JLabel("  C  ");
        cAns = new JLabel("This is answer C");
        c.setBorder(blackline);
        cPnl.add(c);
        cPnl.add(cAns);

        JPanel dPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel d = new JLabel("  D  ");
        d.setBorder(blackline);
        dAns = new JLabel("This is answer D");
        dPnl.add(d);
        dPnl.add(dAns);

        ansPnl.add(aPnl);
        ansPnl.add(bPnl);
        ansPnl.add(cPnl);
        ansPnl.add(dPnl);

        ansPnl.setBorder(blackline);

        add(head);
        add(questionPnl);
        add(Box.createVerticalStrut(10));
        add(qPnl);
        add(ansPnl);
        add(Box.createVerticalStrut(400));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Start")) {
            String selector = Objects.requireNonNull(jComboBox.getSelectedItem()).toString();

            if (selector.equals("Quiz by slang word")) {
                grid.setRows(4);
                grid.setColumns(1);
                ansPnl.setLayout(grid);
                validate();
            }
            else if (selector.equals("Quiz by definition")) {
                System.out.println("Hello");
                grid.setRows(2);
                grid.setColumns(2);
                ansPnl.setLayout(grid);
                validate();
            }
        }
    }
}
