package calculadora;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Calculator extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField resultado;

    private double num1 = 0, num2 = 0, result = 0;
    private char operator;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Calculator frame = new Calculator();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Calculator() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 424, 710);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(207, 160, 160));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(10, 127, 392, 524);
        contentPane.add(panel);
        panel.setLayout(null);

        resultado = new JTextField();
        resultado.setFont(new Font("Arial", Font.BOLD, 24));
        resultado.setHorizontalAlignment(SwingConstants.RIGHT);
        resultado.setText("0");
        resultado.setBounds(10, 10, 392, 95);
        contentPane.add(resultado);
        resultado.setColumns(10);
        resultado.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isDigit(keyChar)) {
                    adicionarNumero(String.valueOf(keyChar));
                    e.consume(); // Consumir o evento para evitar duplicação
                } else if (keyChar == '+' || keyChar == '-' || keyChar == '*' || keyChar == '/') {
                    definirOperacao(keyChar);
                    e.consume();
                } else if (keyChar == KeyEvent.VK_ENTER || keyChar == '=') {
                    calcularResultado();
                    e.consume();
                } else if (keyChar == KeyEvent.VK_BACK_SPACE) {
                    String currentText = resultado.getText();
                    if (currentText.length() > 0) {
                        resultado.setText(currentText.substring(0, currentText.length() - 1));
                    }
                    e.consume();
                } else if (keyChar == '.') {
                    adicionarDecimal();
                    e.consume();
                } else if (keyChar == '%') {
                    double value = Double.parseDouble(resultado.getText());
                    resultado.setText(String.valueOf(value / 100));
                    e.consume();
                }
            }
        });

        JButton JButtonAC = new JButton("AC");
        JButtonAC.setFont(new Font("Arial", Font.BOLD, 16));
        JButtonAC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultado.setText("0");
                num1 = num2 = result = 0;
            }
        });
        JButtonAC.setBounds(10, 10, 85, 94);
        panel.add(JButtonAC);

        JButton JButtonPorcent = new JButton("%");
        JButtonPorcent.setFont(new Font("Arial", Font.BOLD, 16));
        JButtonPorcent.setBounds(200, 10, 85, 94);
        JButtonPorcent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double value = Double.parseDouble(resultado.getText());
                resultado.setText(String.valueOf(value / 100));
            }
        });
        panel.add(JButtonPorcent);

        JButton JButtonMaisMenos = new JButton("+/-");
        JButtonMaisMenos.setFont(new Font("Arial", Font.BOLD, 16));
        JButtonMaisMenos.setBounds(105, 10, 85, 94);
        JButtonMaisMenos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double value = Double.parseDouble(resultado.getText());
                resultado.setText(String.valueOf(value * -1));
            }
        });
        panel.add(JButtonMaisMenos);

        adicionarBotao(panel, "7", 10, 112, e -> adicionarNumeroBotao("7"));
        adicionarBotao(panel, "8", 105, 114, e -> adicionarNumeroBotao("8"));
        adicionarBotao(panel, "9", 200, 112, e -> adicionarNumeroBotao("9"));
        adicionarBotao(panel, "/", 295, 10, e -> definirOperacao('/'));
        adicionarBotao(panel, "4", 10, 216, e -> adicionarNumeroBotao("4"));
        adicionarBotao(panel, "5", 105, 216, e -> adicionarNumeroBotao("5"));
        adicionarBotao(panel, "6", 200, 216, e -> adicionarNumeroBotao("6"));
        adicionarBotao(panel, "*", 295, 114, e -> definirOperacao('*'));
        adicionarBotao(panel, "1", 10, 316, e -> adicionarNumeroBotao("1"));
        adicionarBotao(panel, "2", 105, 316, e -> adicionarNumeroBotao("2"));
        adicionarBotao(panel, "3", 200, 316, e -> adicionarNumeroBotao("3"));
        adicionarBotao(panel, "-", 295, 216, e -> definirOperacao('-'));
        adicionarBotao(panel, "0", 105, 420, e -> adicionarNumeroBotao("0"));
        adicionarBotao(panel, ".", 200, 420, e -> adicionarDecimal());
        adicionarBotao(panel, "+", 295, 316, e -> definirOperacao('+'));
        adicionarBotao(panel, "=", 295, 420, e -> calcularResultado());

        JButton JButtonFechar = new JButton("Close");
        JButtonFechar.setFont(new Font("Arial", Font.BOLD, 16));
        JButtonFechar.addActionListener(e -> System.exit(0));
        JButtonFechar.setBounds(10, 420, 85, 94);
        panel.add(JButtonFechar);
    }

    private void adicionarBotao(JPanel panel, String label, int x, int y, ActionListener acao) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBounds(x, y, 85, 94);
        button.addActionListener(acao);
        panel.add(button);
    }

    private void adicionarNumero(String numero) {
        if (resultado.getText().equals("0")) {
            resultado.setText(numero);
        } else {
            resultado.setText(resultado.getText() + numero);
        }
    }

    private void adicionarNumeroBotao(String numero) {
        if (resultado.getText().equals("0")) {
            resultado.setText(numero);
        } else {
            resultado.setText(resultado.getText() + numero);
        }
    }

    private void adicionarDecimal() {
        if (!resultado.getText().contains(".")) {
            resultado.setText(resultado.getText() + ".");
        }
    }

    private void definirOperacao(char operacao) {
        num1 = Double.parseDouble(resultado.getText());
        operator = operacao;
        resultado.setText("0");
    }

    private void calcularResultado() {
        num2 = Double.parseDouble(resultado.getText());
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    resultado.setText("Erro");
                    return;
                }
                break;
        }
        resultado.setText(String.valueOf(result));
        num1 = result;
    }
}
