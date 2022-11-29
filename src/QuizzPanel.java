import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * PACKAGE_NAME
 * Created by PC
 * Date 11/29/2022 - 11:38 AM
 * Description: ...
 */
public class QuizzPanel extends JPanel implements ActionListener, ItemListener, MouseListener {
    private SlangDictionary slangDictionary;
    private JComboBox<String> jComboBox;
    private JLabel question;
    private JLabel qContent;
    private JLabel a, b, c, d;
    private JLabel aAns;
    private JLabel bAns;
    private JLabel cAns;
    private JLabel dAns;
    private GridLayout grid;
    private JPanel ansPnl;
    private JButton startBtn;
    private JButton nextBtn;
    private boolean isAnswered = false;
    private String[] randomQuiz;

    private JLabel[] answers;
    public QuizzPanel(SlangDictionary slangDic) {
        slangDictionary = slangDic;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Border blackline = BorderFactory.createLineBorder(Color.black);

        Font font = new Font("Verdana", Font.BOLD, 35);
        Font fontBtn = new Font("Verdana", Font.BOLD, 25);
        Font qstFont = new Font("Verdana", Font.BOLD, 20);
        Font ansFont = new Font("Verdana", Font.BOLD, 20);


        String[] options = {"Quiz by slang word", "Quiz by definition"};
        JPanel head = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        head.setBorder(blackline);
        jComboBox = new JComboBox<>(options);
        jComboBox.addItemListener(this);
        startBtn = new JButton("Start");
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
        a = new JLabel("  A  ");
        a.setFont(ansFont);
        a.setBorder(blackline);
        aAns = new JLabel("This is answer A");
        aAns.setFont(ansFont);
        aPnl.add(a);
        aPnl.add(aAns);

        JPanel bPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        b = new JLabel("  B  ");
        b.setFont(ansFont);
        bAns = new JLabel("This is answer B");
        bAns.setFont(ansFont);
        b.setBorder(blackline);
        bPnl.add(b);
        bPnl.add(bAns);

        JPanel cPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        c = new JLabel("  C  ");
        c.setFont(ansFont);
        cAns = new JLabel("This is answer C");
        cAns.setFont(ansFont);
        c.setBorder(blackline);
        cPnl.add(c);
        cPnl.add(cAns);

        JPanel dPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        d = new JLabel("  D  ");
        d.setFont(ansFont);
        d.setBorder(blackline);
        dAns = new JLabel("This is answer D");
        dAns.setFont(ansFont);
        dPnl.add(d);
        dPnl.add(dAns);

        a.addMouseListener(this);
        b.addMouseListener(this);
        c.addMouseListener(this);
        d.addMouseListener(this);

        a.setOpaque(true);
        b.setOpaque(true);
        c.setOpaque(true);
        d.setOpaque(true);

        ansPnl.add(aPnl);
        ansPnl.add(bPnl);
        ansPnl.add(cPnl);
        ansPnl.add(dPnl);

        ansPnl.setBorder(blackline);
        ansPnl.setVisible(false);
        qContent.setVisible(false);

        answers = new JLabel[]{a, b, c, d};

        JPanel nextPnl = new JPanel(new FlowLayout());
        nextBtn = new JButton("Next");
        nextBtn.setPreferredSize(new Dimension(100, 40));
        nextPnl.add(nextBtn);
        nextBtn.addActionListener(this);
        nextBtn.setVisible(false);

        add(head);
        add(questionPnl);
        add(Box.createVerticalStrut(35));
        add(qPnl);
        add(ansPnl);
        add(Box.createVerticalStrut(25));
        add(nextPnl);
        add(Box.createVerticalStrut(400));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Start") || (cmd.equals("Next") && isAnswered)) {
            resetAllChoice();
            ansPnl.setVisible(true);
            qContent.setVisible(true);
            startBtn.setEnabled(false);
            nextBtn.setEnabled(false);
            String selector = Objects.requireNonNull(jComboBox.getSelectedItem()).toString();

            nextBtn.setVisible(true);

            if (selector.equals("Quiz by slang word")) {
                grid.setRows(4);
                grid.setColumns(1);
                grid.setVgap(15);
                ansPnl.setLayout(grid);
                validate();

                randomQuiz = slangDictionary.generateQuizBySlang();
                Random r = new Random();
                int n = r.nextInt(3);
                switch (n) {
                    case 0 -> {
                        qContent.setText("What is the meaning of `" + randomQuiz[0] + "` ?");
                        break;
                    }
                    case 1 -> {
                        qContent.setText("What does `" + randomQuiz[0] + "` stand for?");
                        break;
                    }
                    case 2 -> {
                        qContent.setText("What does `" + randomQuiz[0] + "` mean?");
                    }
                }

                aAns.setText(randomQuiz[2]);
                bAns.setText(randomQuiz[3]);
                cAns.setText(randomQuiz[4]);
                dAns.setText(randomQuiz[5]);

            }
            else if (selector.equals("Quiz by definition")) {
                grid.setRows(2);
                grid.setColumns(2);
                grid.setVgap(15);
                ansPnl.setLayout(grid);
                validate();

                randomQuiz = slangDictionary.generateQuizByDef();
                qContent.setText("Which slang word will be fit with sentence `" + randomQuiz[0] + "`?");

                aAns.setText(randomQuiz[2]);
                bAns.setText(randomQuiz[3]);
                cAns.setText(randomQuiz[4]);
                dAns.setText(randomQuiz[5]);
            }
            nextBtn.setEnabled(false);
            isAnswered = false;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == jComboBox) {
            startBtn.setEnabled(true);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!isAnswered) {
            int answer = Integer.parseInt(randomQuiz[1]) - 2;
            if (e.getSource() == a) {
                if (!randomQuiz[1].equals(aAns.getText())) {
                    a.setBackground(Color.RED);
                }
                a.setForeground(Color.WHITE);
            }
            else if (e.getSource() == b) {
                if (!randomQuiz[1].equals(bAns.getText())) {
                    b.setBackground(Color.RED);
                }
                b.setForeground(Color.WHITE);
            }
            else if (e.getSource() == c) {
                if (!randomQuiz[1].equals(cAns.getText())) {
                    c.setBackground(Color.RED);
                }
                c.setForeground(Color.WHITE);
            }
            else if (e.getSource() == d) {
                if (!randomQuiz[1].equals(dAns.getText())) {
                    d.setBackground(Color.RED);
                }
                d.setForeground(Color.WHITE);
            }
            isAnswered = true;
            answers[answer].setBackground(Color.GREEN);
            answers[answer].setForeground(Color.WHITE);
            nextBtn.setEnabled(true);
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
        if (!isAnswered) {
            if (e.getSource() == a) {
                setColor(a);
            }
            else if (e.getSource() == b) {
                setColor(b);
            }
            else if (e.getSource() == c) {
                setColor(c);
            }
            else if (e.getSource() == d) {
                setColor(d);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!isAnswered) {
            if (e.getSource() == a) {
                resetColor(a);
            }
            else if (e.getSource() == b) {
                resetColor(b);
            }
            else if (e.getSource() == c) {
                resetColor(c);
            }
            else if (e.getSource() == d) {
                resetColor(d);
            }
        }
    }

    private void setColor(JLabel lb) {
        lb.setBackground(Color.BLUE);
        lb.setForeground(Color.WHITE);;
    }

    private void resetColor(JLabel lb) {
        lb.setBackground(getBackground());
        lb.setForeground(getForeground());
    }

    private void resetAllChoice() {
        resetColor(a);
        resetColor(b);
        resetColor(c);
        resetColor(d);
    }
}
