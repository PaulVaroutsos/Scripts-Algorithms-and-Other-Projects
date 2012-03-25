
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ExpressionGUI {

    //Frame
    private JFrame frame = new JFrame("Expression Evaluator");
    //Radio buttons to indicate what kind of expression to generate
    private JRadioButton prefixButton = new JRadioButton("Prefix");
    private JRadioButton postfixButton = new JRadioButton("Postfix");
    private JRadioButton infixButton = new JRadioButton("Infix", false);
    //Label expression field
    private JLabel inputExpressionLabel = new JLabel("  Enter your expression:");
    //Field to input expression to save
    private JTextField inputExpressionField = new JTextField(40);
    //The answer label
    private JLabel answerLabel = new JLabel();
    private JLabel theAnswer = new JLabel();
    //Buttons
    private JButton calculateButton = new JButton("Calculate");
    private JButton saveExpression = new JButton("Save Expression");
    private JButton generateExpression = new JButton("Generate Expression");
    private JButton clearButton = new JButton("Clear");
    //Indicates the type of expression to generate, this is set by
    //the radio buttons
    int expressionType = 0;
    //Components of the GUI
    JPanel fields;
    JPanel radioButtonPanel;
    Container contentPane;
    //The expression that is saved.
    Expression expression;

    //Creates the GUI
    public ExpressionGUI() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width / 2 - frame.getWidth() / 2, d.height / 2
                - frame.getHeight() / 2);

        setActionListeners();

        createGUI();
    }

    //Sets actionlisteners for buttons, radio buttons
    private void setActionListeners() {
        prefixButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                buildPrefixGUI();
            }
        });

        postfixButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                buildPostFixGUI();
            }
        });

        infixButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                buildInfixGUI();
            }
        });

        calculateButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });

        saveExpression.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                saveExpression(inputExpressionField.getText());
            }
        });

        clearButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        generateExpression.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                generate();
            }
        });
    }

    //Creates the gui by inserting the objects into the frame
    private void createGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        calculateButton.setSize(2, 4);
        saveExpression.setSize(2, 4);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem closeItem = new JMenuItem("Close");
        closeItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });

        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                main(null);
            }
        });

        fileMenu.add(newItem);
        fileMenu.add(closeItem);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        editMenu.add(clearItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                about();
            }
        });

        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                help();
            }
        });


        //Add items to the gui
        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel radioButtonPanel = new JPanel(new GridLayout(1, 0));
        radioButtonPanel.add(prefixButton);
        radioButtonPanel.add(postfixButton);
        radioButtonPanel.add(infixButton);
        radioButtonPanel.add(clearButton);
        contentPane.add(radioButtonPanel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel(new GridLayout(6, 1));
        textPanel.add(inputExpressionLabel);
        contentPane.add(textPanel, BorderLayout.WEST);

        JPanel inputPanel = new JPanel(new GridLayout(6, 1));

        inputPanel.add(inputExpressionField);

        contentPane.add(inputPanel, BorderLayout.EAST);

        JPanel answerPanel = new JPanel(new GridLayout(2, 2));

        answerPanel.add(calculateButton);
        answerPanel.add(saveExpression);
        answerPanel.add(generateExpression);
        answerPanel.add(answerLabel);
        answerPanel.add(theAnswer);

        contentPane.add(answerPanel, BorderLayout.SOUTH);

        frame.setJMenuBar(menuBar);
        frame.setBounds(400, 400, 400, 400);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }

    //Adjust for each expression radio button when toggled
    private void buildPrefixGUI() {
        prefixButton.setSelected(true);
        postfixButton.setSelected(false);
        infixButton.setSelected(false);
        expressionType = 1;
    }

    //Adjust for each expression radio button when toggled
    private void buildPostFixGUI() {
        prefixButton.setSelected(false);
        postfixButton.setSelected(true);
        infixButton.setSelected(false);
        expressionType = 2;
    }

    //Adjust for each expression radio button when toggled
    private void buildInfixGUI() {
        infixButton.setSelected(true);
        postfixButton.setSelected(false);
        prefixButton.setSelected(false);

        answerLabel.setText("");
        inputExpressionField.setText("");
        theAnswer.setText("");
        expressionType = 3;
    }

    //Calculate the expression.  This uses the Expression class.
    private void calculate() {
        String exp = inputExpressionField.getText();
        try {
            expression = new Expression(exp);
        } catch (Exception e) {
            improperInput();
        }
        if (expression == null) {
            theAnswer.setText("No expression to evaluate.");
        } else {
            answerLabel.setText("The answer is: ");
            theAnswer.setText(expression.evaluate() + "");
        }
    }

    //Generate an expression and display it
    private void generate() {
        String generatedExp = ExpressionGenerator.generateExpression(expressionType);
        inputExpressionField.setText(generatedExp);
    }

    //Save the expression into the expression field
    private void saveExpression(String exp) {
        StringSelection stringSelection = new StringSelection(exp);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    //Clears the GUI of text
    private void clear() {
        expression = null;
        inputExpressionField.setText("");
        answerLabel.setText("");
        theAnswer.setText("");
        infixButton.setSelected(false);
        postfixButton.setSelected(false);
        prefixButton.setSelected(false);
        expressionType = 0;
    }

    //Quit the program
    private void quit() {
        System.exit(0);
    }

    //About the program
    private void about() {
        JOptionPane.showMessageDialog(frame,
                "Expression Evaluator\n" + "Version 1.0\n"
                + "Please enter a valid infix, prefix, or postfix expression",
                "About Expression Evaluator", JOptionPane.INFORMATION_MESSAGE);
    }

    //Prompt that tells user that hte expression is invalid
    private void improperInput() {
        JOptionPane.showMessageDialog(frame,
                "Expression Evaluator\n" + "Please enter a valid expression",
                "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
    }

    //Help, about the program
    private void help() {
        JOptionPane.showMessageDialog(
                frame,
                "If you would like to evaluate an expression, just type in"
                + "the expression and click save and then evaluate.\n"
                + "If you would like to generate one randomly, click the appropriate"
                + "radio button and click generate.",
                "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ExpressionGUI exp = new ExpressionGUI();
    }
}
