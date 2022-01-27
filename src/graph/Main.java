package src.graph;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;

import java.util.Arrays;
import java.util.List;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

class Main implements ActionListener {
    private String znak1 = "", znak2 = "";
    private JLabel setOfElements;
    private JFrame viewForm;

    private String dataSr;
    private JTextField input;
    private JTextArea textArea;
    private Integer dataCounter = 0;

    private Integer elementsCount = 0;

    private Integer[] elements;

    public static Integer[] removeElementUsingCollection(Integer[] arr, int index) {
        List<Integer> tempList = Arrays.asList(arr);
        tempList.remove(index);
        return tempList.toArray(new Integer[0]);
    }

    public void createFrame() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                dataSr = "Введіть кількість елементів: ";
                JFrame frame = new JFrame("Data");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);
                textArea = new JTextArea(15, 50);
                textArea.setText(dataSr);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);
                textArea.setFont(Font.getFont(Font.SANS_SERIF));
                JScrollPane scroller = new JScrollPane(textArea);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                JPanel inputpanel = new JPanel();
                inputpanel.setLayout(new FlowLayout());

                input = new JTextField(20);
                JButton button = new JButton("Enter");
                DefaultCaret caret = (DefaultCaret) textArea.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                panel.add(scroller);
                inputpanel.add(input);
                inputpanel.add(button);
                panel.add(inputpanel);
                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);
                input.requestFocus();

                button.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (dataCounter == 0 && Integer.parseInt(input.getText()) > 0) {
                            dataSr += "\n" + input.getText() + "\n" + "Введіть елементи множини:";
                            textArea.setText(dataSr);
                            elementsCount = Integer.parseInt(input.getText());
                            input.setText("");
                            dataCounter++;
                        } else {
                            String[] words = input.getText().split(" ");

                            elements = new Integer[elementsCount];

                            for (int i = 0; i < elementsCount; i++) {
                                elements[i] = Integer.parseInt(words[i]);
                            }
                            for (int i = 0; i < elementsCount; i++) {
                                for (int j = 0; j < elementsCount; j++) {
                                    if (elements[i] == elements[j] && i != j) {
                                        elements = removeElementUsingCollection(elements, i);
                                    }
                                }
                            }
                            setOfElements.setText(
                                    "A = {" + Arrays.toString(elements).replace("[", "").replace("]", "") + "}");
                            dataCounter = 0;
                            frame.dispose();
                        }
                    }
                });
            }

        });
    }

    public Main() {
        initComponents();
    }

    public void initComponents() {
        viewForm = new JFrame("Main Form");
        viewForm.setSize(650, 650);
        viewForm.setVisible(true);
        viewForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel instr = new JLabel("Оберіть опції");
        instr.setFont(new Font("TimesRoman", Font.PLAIN, 28));
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(instr, constraints);

        JPanel panel2 = new JPanel(new GridBagLayout());
        GridBagConstraints constraints2 = new GridBagConstraints();

        JLabel xLabel = new JLabel("X");
        xLabel.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        constraints2.ipadx = 20;
        panel2.add(xLabel, constraints2);

        JLabel znak1Label = new JLabel();
        znak1Label.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        constraints2.ipadx = 20;
        panel2.add(znak1Label, constraints2);

        JLabel yLabel = new JLabel("Y");
        yLabel.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        constraints2.ipadx = 20;
        panel2.add(yLabel, constraints2);

        JLabel znak2Label = new JLabel();
        znak2Label.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        constraints2.ipadx = 20;
        panel2.add(znak2Label, constraints2);

        JTextField firstSymbol = new JTextField();
        firstSymbol.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        firstSymbol.setHorizontalAlignment(JTextField.CENTER);
        panel2.add(firstSymbol, constraints2);

        constraints.gridx = 1;
        constraints.gridy = 6;
        panel.add(panel2, constraints);

        JButton button = new JButton("Ввести дані");
        button.setVisible(true);
        constraints.gridx = 0;
        constraints.gridy = 9;
        panel.add(button, constraints);

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame();
            }
        });

        JButton button2 = new JButton("Побудувати граф");
        button2.setVisible(true);
        constraints.gridx = 2;
        constraints.gridy = 9;
        panel.add(button2, constraints);

        button2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String num = firstSymbol.getText();
                Integer number = Integer.parseInt(num);
                createGraph(znak1, znak2, number);
            }
        });

        constraints.ipady = 15;

        setOfElements = new JLabel("A = {}");
        setOfElements.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        constraints.gridx = 0;
        constraints.gridy = 10;
        panel.add(setOfElements, constraints);

        ButtonGroup radioPanel1 = new ButtonGroup();
        JRadioButton birdButton = new JRadioButton("+");
        JRadioButton catButton = new JRadioButton("-");
        JRadioButton dogButton = new JRadioButton("*");
        JRadioButton rabbitButton = new JRadioButton("/");
        JRadioButton pigButton = new JRadioButton("%");

        radioPanel1.add(birdButton);
        radioPanel1.add(catButton);
        radioPanel1.add(dogButton);
        radioPanel1.add(rabbitButton);
        radioPanel1.add(pigButton);

        ActionListener sliceActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton aButton = (AbstractButton) actionEvent.getSource();
                znak1 = aButton.getText();
                znak1Label.setText(znak1);
            }
        };

        birdButton.addActionListener(sliceActionListener);
        catButton.addActionListener(sliceActionListener);
        dogButton.addActionListener(sliceActionListener);
        rabbitButton.addActionListener(sliceActionListener);
        pigButton.addActionListener(sliceActionListener);

        constraints.ipady = 10;

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(birdButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(catButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(dogButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(rabbitButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(pigButton, constraints);

        ButtonGroup radioPanel2 = new ButtonGroup();
        JRadioButton birdButton2 = new JRadioButton(">");
        JRadioButton catButton2 = new JRadioButton("<");
        JRadioButton dogButton2 = new JRadioButton(">=");
        JRadioButton rabbitButton2 = new JRadioButton("<=");
        JRadioButton pigButton2 = new JRadioButton("=");

        radioPanel2.add(birdButton2);
        radioPanel2.add(catButton2);
        radioPanel2.add(dogButton2);
        radioPanel2.add(rabbitButton2);
        radioPanel2.add(pigButton2);

        ActionListener sliceActionListener2 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton aButton = (AbstractButton) actionEvent.getSource();
                znak2 = aButton.getText();
                znak2Label.setText(znak2);
            }
        };

        birdButton2.addActionListener(sliceActionListener2);
        catButton2.addActionListener(sliceActionListener2);
        dogButton2.addActionListener(sliceActionListener2);
        rabbitButton2.addActionListener(sliceActionListener2);
        pigButton2.addActionListener(sliceActionListener2);

        constraints.gridx = 2;
        constraints.gridy = 1;
        panel.add(birdButton2, constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        panel.add(catButton2, constraints);

        constraints.gridx = 2;
        constraints.gridy = 3;
        panel.add(dogButton2, constraints);

        constraints.gridx = 2;
        constraints.gridy = 4;
        panel.add(rabbitButton2, constraints);

        constraints.gridx = 2;
        constraints.gridy = 5;
        panel.add(pigButton2, constraints);

        viewForm.add(panel);
    }

    private void createGraph(String znak1, String znak2, Integer number) {
        GraphDraw frame = new GraphDraw("Graphes");
        Integer k;
        k = elementsCount;

        Integer[] array = elements;

        frame.setSize(1000, 1000);
        frame.setVisible(true);

        for (int i = 0; i < k; i++) {
            int nx = (int) ((-1) * Math.sin(Math.toRadians(360 / k * i)) * 40 * k);
            int ny = (int) (Math.cos(Math.toRadians(360 / k * i)) * 40 * k);
            frame.addNode("" + (array[i]), nx + frame.getSize().width / 2, ny + frame.getSize().height / 2);
        }
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                if (znak1.equals("+")) {
                    if (znak2.equals("<")) {
                        if (array[i] + array[j] < number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals(">")) {
                        if (array[i] + array[j] > number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals("<=")) {
                        if (array[i] + array[j] <= number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals(">=")) {
                        if (array[i] + array[j] >= number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals("=")) {
                        if (array[i] + array[j] == number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals("%")) {
                        if ((array[i] + array[j]) % number == 0) {
                            frame.addEdge(i, j);
                        }
                    }

                }
                if (znak1.equals("-")) {
                    if (znak2.equals("<")) {
                        if (array[i] - array[j] < number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals(">")) {
                        if (array[i] - array[j] > number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals("<=")) {
                        if (array[i] - array[j] <= number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals(">=")) {
                        if (array[i] - array[j] >= number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals("=")) {
                        if (array[i] - array[j] == number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals("%")) {
                        if ((array[i] - array[j]) % number == 0) {
                            frame.addEdge(i, j);
                        }
                    }
                }
                if (znak1.equals("*")) {
                    if (znak2.equals("<")) {
                        if (array[i] * array[j] < number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals(">")) {
                        if (array[i] * array[j] > number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals("<=")) {
                        if (array[i] * array[j] <= number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals(">=")) {
                        if (array[i] * array[j] >= number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals("=")) {
                        if (array[i] * array[j] == number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals("%")) {
                        if ((array[i] * array[j]) % number == 0) {
                            frame.addEdge(i, j);
                        }
                    }
                }
                if (znak1.equals("/")) {
                    if (znak2.equals("<")) {
                        if (array[i] / array[j] < number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals(">")) {
                        if (array[i] / array[j] > number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals("<=")) {
                        if (array[i] / array[j] <= number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals(">=")) {
                        if (array[i] / array[j] >= number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals("=")) {
                        if (array[i] / array[j] == number) {
                            frame.addEdge(i, j);
                        }
                    }
                    if (znak2.equals("%")) {
                        if ((array[i] / array[j]) % number == 0) {
                            frame.addEdge(i, j);
                        }
                    }
                }
                if (znak1.equals("%")) {
                    if (array[i] % array[j] == 0) {
                        frame.addEdge(i, j);
                    }
                }
            }
        }
    }

    public void actionPerformed(ActionEvent action) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}