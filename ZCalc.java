import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZCalc extends JFrame {
    private JTextField display;
    private State state;

    public ZCalc() {
        state = new State();
        setTitle("calculator");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Background gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(30, 30, 30), 0, getHeight(), new Color(20, 20, 20));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel);

        display = new JTextField();
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setFont(new Font("Helvetica Neue", Font.PLAIN, 40));
        display.setBackground(new Color(40, 40, 40));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        backgroundPanel.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setOpaque(false);

        String[] buttons = {
            "C", "", "", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", ""
        };

        for (String text : buttons) {
            if (text.isEmpty()) {
                buttonPanel.add(new JLabel()); // Empty space for alignment
            } else {
                RoundedButton button = new RoundedButton(text);
                button.addActionListener(new ButtonClickListener());
                if ("C".equals(text)) {
                    button.setBackground(new Color(200, 60, 60));
                } else if ("=".equals(text)) {
                    button.setBackground(new Color(50, 120, 200));
                } else if ("+-*/".contains(text)) {
                    button.setBackground(new Color(240, 140, 50));
                }
                buttonPanel.add(button);
            }
        }

        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            try {
                if ("0123456789.".contains(command)) {
                    display.setText(display.getText() + command);
                } else if ("+-*/".contains(command)) {
                    if (!display.getText().isEmpty()) {
                        state.inputNumber(Double.parseDouble(display.getText()));
                        display.setText("");
                    }
                    state.inputOperator(command);
                } else if ("=".equals(command)) {
                    if (!display.getText().isEmpty()) {
                        state.inputNumber(Double.parseDouble(display.getText()));
                    }
                    display.setText(String.valueOf(state.getResult()));
                } else if ("C".equals(command)) {
                    state.reset();
                    display.setText("");
                }
            }
            catch (Exception exception) {
                display.setText("Dividing by zero, you got no rizz");
                System.out.println(exception.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ZCalc calculator = new ZCalc();
            calculator.setVisible(true);
        });
    }
}
