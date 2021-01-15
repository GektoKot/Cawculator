package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame{
    private final JButton calculate;
    private final JTextField textField;
    private final JLabel answer;
    private final Handler handler = new Handler();

    public Frame(String title) throws HeadlessException {
        super(title);
        setLayout(new FlowLayout());
        calculate = new JButton("CaWculate!");
        textField = new JTextField(50);
        answer = new JLabel("");
//        textField.setSize(new Dimension(600,50));
        add(textField);
        add(calculate);
        add(answer);
        calculate.addActionListener(handler);
    }

    public class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == calculate) {
                String expression = textField.getText();
                answer.setText("Answer: " + Cawculator.cawculateIt(expression));
            }

        }
    }
}
