import javax.swing.*;
import java.awt.*;

/**
 * Created by PC
 * Date 11/26/2022 - 1:12 PM
 * Description: ...
 */
public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Slang Dictionary Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

//        tabbedPane.setPreferredSize();

        JLabel titleLabel = new JLabel("Slang Dictionary Application", JLabel.CENTER);

//        JLabel searchLabel = new JLabel();
        JPanel searchLabel = new SearchSlangPanel();
        JLabel findSlangLabel = new JLabel();
        JLabel editSlangLabel = new JLabel();
        JLabel historyLabel = new JLabel();
        JLabel randomLabel = new JLabel();
        JLabel quizzLabel = new JLabel();

        tabbedPane.add("Search Slang Word", searchLabel);
        tabbedPane.add("Find Slang Word", findSlangLabel);
        tabbedPane.add("Edit Slang Word", editSlangLabel);
        tabbedPane.add("History", historyLabel);
        tabbedPane.add("Random", randomLabel);
        tabbedPane.add("Quizzes", quizzLabel);

        add(titleLabel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame app = new MainFrame();
    }
}
