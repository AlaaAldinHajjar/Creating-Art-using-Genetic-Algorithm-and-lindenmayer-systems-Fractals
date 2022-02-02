package gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import l.system.with.ga.GeneticAlgorithm;

import l.system.with.ga.LSystemWithGA;
import l.system.with.ga.Individual;
import l.system.with.ga.Population;

/**
 * Implements the application's GUI.
 */
@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener {

    private int gen = 0;
    /**
     * Canvas to draw on.
     */

    int frameSize = 6;
    int popsize = 120;
    int ind = 0;
    int end = popsize / 6;
    private Canvas[] canvasPanel = new Canvas[frameSize];

    /**
     * Text field for axioms / starting conditions.
     */
    private JTextField[] startTextField = new JTextField[frameSize];
    /**
     * Text area for rules.
     */
    private JTextArea[] rulesTextArea = new JTextArea[frameSize];

    /**
     * Text field for iterations.
     */
    private JTextField[] iterationsTextField = new JTextField[frameSize];
    /**
     * Text field for turning angle.
     */
    private JTextField[] angleTextField = new JTextField[frameSize];

    /**
     * Text field for line length.
     */
    private JTextField[] lineTextField = new JTextField[frameSize];

    private JTextField[] fitness = new JTextField[frameSize];

    private JTextField GenTextField;

    private JLabel GenLable;

    /**
     * Button group for origin radio buttons.
     */
    private ButtonGroup originButtonGroup;
    /**
     * Corner start radio button.
     */
    private JRadioButton cornerButton;
    /**
     * Center start radio button.
     */
    private JRadioButton centerButton;
    /**
     * Bottom start radio button.
     */
    private JRadioButton bottomButton;
    /**
     * Check box for vertical or default start.
     */
    private JCheckBox verticalCheckBox;
    private JCheckBox randomCheckBox;
    private JCheckBox ModifyCheckBox;
    private JButton drawButton;
    private JButton exportButton;
    private JButton importButton;

    private File file;
    private FileWriter fw;
    private PrintWriter pw;
    private Scanner sc;

    private GeneticAlgorithm ga = new GeneticAlgorithm(popsize, 0.01, 0.95, 6);
    Population pop = ga.initPopulation();

    /**
     * Constructor.
     */
    public Frame() throws IOException {

        setTitle("");

        initUI();

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Creates the UI and all the elements inside.
     */
    public final void initUI() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        getContentPane().add(mainPanel);

        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 5)));

        JPanel CanvasPanel = new JPanel(new GridBagLayout());
        CanvasPanel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 5)));

        JPanel TitlePanel = new JPanel(new GridBagLayout());
        TitlePanel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 5)));
        for (int i = 0; i < frameSize; i++) {
            canvasPanel[i] = new Canvas();
            canvasPanel[i].setBackground(Color.white);
            canvasPanel[i].setPreferredSize(new Dimension(490, 490));
            canvasPanel[i].setBorder(new LineBorder(Color.black));
        }

        GridBagConstraints constraints = new GridBagConstraints();
        JLabel titleLabel = new JLabel("L-Systems with GA");
        JLabel[] startLabel = new JLabel[frameSize];
        JLabel[] rulesLabel = new JLabel[frameSize];
        JLabel[] iterationsLabel = new JLabel[frameSize];
        JLabel[] angleLabel = new JLabel[frameSize];
        JLabel[] lineLabel = new JLabel[frameSize];
        JLabel[] fitnessLable = new JLabel[frameSize];
        for (int i = 0; i < frameSize; i++) {
            startLabel[i] = new JLabel("Start " + (i + 1));
            rulesLabel[i] = new JLabel("Rules " + (i + 1));
            iterationsLabel[i] = new JLabel("Iterations " + (i + 1));
            angleLabel[i] = new JLabel("Angle " + (i + 1));
            lineLabel[i] = new JLabel("Line length " + (i + 1));
            fitnessLable[i] = new JLabel("Fitness " + (i + 1));
            startTextField[i] = new JTextField();
            rulesTextArea[i] = new JTextArea(4, 12);
            iterationsTextField[i] = new JTextField();
            angleTextField[i] = new JTextField();
            lineTextField[i] = new JTextField();
            fitness[i] = new JTextField();
        }

        GenLable = new JLabel("The Generation = ");

        JLabel originLabel = new JLabel("Origin");

        // Number of rows overridden by the layout.
        GenTextField = new JTextField();

        cornerButton = new JRadioButton("corner");
        centerButton = new JRadioButton("center");
        bottomButton = new JRadioButton("bottom");
        originButtonGroup = new ButtonGroup();
        verticalCheckBox = new JCheckBox("Vertical");
        randomCheckBox = new JCheckBox("Random");
        ModifyCheckBox = new JCheckBox("Modify");
        drawButton = new JButton("Draw");
        exportButton = new JButton("Export");
        importButton = new JButton("Import");

        originButtonGroup.add(cornerButton);
        originButtonGroup.add(centerButton);
        originButtonGroup.add(bottomButton);
        constraints.insets = new Insets(2, 1, 2, 1);
        constraints.anchor = GridBagConstraints.NORTHWEST;
        mainPanel.add(menuPanel);
        mainPanel.add(CanvasPanel);

        constraints.gridy = 0;
        constraints.gridx = 2;
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menuPanel.add(titleLabel, constraints);
        constraints.gridy = 1;
        constraints.gridx = 0;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 2;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 4;
        menuPanel.add(new JSeparator(), constraints);

        constraints.gridy = 0;
        constraints.gridx = 3;
        CanvasPanel.add(canvasPanel[0], constraints);
        constraints.gridy = 0;
        constraints.gridx = 4;
        CanvasPanel.add(canvasPanel[1], constraints);
        constraints.gridy = 0;
        constraints.gridx = 5;
        CanvasPanel.add(canvasPanel[2], constraints);
        constraints.gridy = 1;
        constraints.gridx = 3;
        CanvasPanel.add(canvasPanel[3], constraints);
        constraints.gridy = 1;
        constraints.gridx = 4;
        CanvasPanel.add(canvasPanel[4], constraints);
        constraints.gridy = 1;
        constraints.gridx = 5;
        CanvasPanel.add(canvasPanel[5], constraints);

        constraints.gridx = 0;
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.anchor = GridBagConstraints.NORTHWEST;

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        menuPanel.add(startLabel[0], constraints);
        constraints.gridx = 2;
        menuPanel.add(startLabel[1], constraints);
        constraints.gridx = 4;
        menuPanel.add(startLabel[2], constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(startTextField[0], constraints);
        constraints.gridx = 2;
        menuPanel.add(startTextField[1], constraints);
        constraints.gridx = 4;
        menuPanel.add(startTextField[2], constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        menuPanel.add(rulesLabel[0], constraints);
        constraints.gridx = 2;
        menuPanel.add(rulesLabel[1], constraints);
        constraints.gridx = 4;
        menuPanel.add(rulesLabel[2], constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        rulesTextArea[0].setLineWrap(true);
        menuPanel.add(new JScrollPane(rulesTextArea[0]), constraints);
        constraints.gridx = 2;
        rulesTextArea[1].setLineWrap(true);
        menuPanel.add(new JScrollPane(rulesTextArea[1]), constraints);
        constraints.gridx = 4;
        rulesTextArea[2].setLineWrap(true);
        menuPanel.add(new JScrollPane(rulesTextArea[2]), constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        menuPanel.add(iterationsLabel[0], constraints);
        constraints.gridx = 2;
        menuPanel.add(iterationsLabel[1], constraints);
        constraints.gridx = 4;
        menuPanel.add(iterationsLabel[2], constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(iterationsTextField[0], constraints);
        constraints.gridx = 2;
        menuPanel.add(iterationsTextField[1], constraints);
        constraints.gridx = 4;
        menuPanel.add(iterationsTextField[2], constraints);

        constraints.gridx = 0;
        constraints.gridy = 8;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 2;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 4;
        menuPanel.add(new JSeparator(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        menuPanel.add(angleLabel[0], constraints);
        constraints.gridx = 2;
        menuPanel.add(angleLabel[1], constraints);
        constraints.gridx = 4;
        menuPanel.add(angleLabel[2], constraints);

        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(angleTextField[0], constraints);
        constraints.gridx = 2;
        menuPanel.add(angleTextField[1], constraints);
        constraints.gridx = 4;
        menuPanel.add(angleTextField[2], constraints);

        constraints.gridx = 0;
        constraints.gridy = 11;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        menuPanel.add(lineLabel[0], constraints);
        constraints.gridx = 2;
        menuPanel.add(lineLabel[1], constraints);
        constraints.gridx = 4;
        menuPanel.add(lineLabel[2], constraints);

        constraints.gridx = 0;
        constraints.gridy = 12;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(lineTextField[0], constraints);
        constraints.gridx = 2;
        menuPanel.add(lineTextField[1], constraints);
        constraints.gridx = 4;
        menuPanel.add(lineTextField[2], constraints);

        constraints.gridx = 0;
        constraints.gridy = 13;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 2;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 4;
        menuPanel.add(new JSeparator(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 14;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 2;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 4;
        menuPanel.add(new JSeparator(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 15;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        menuPanel.add(startLabel[3], constraints);
        constraints.gridx = 2;
        menuPanel.add(startLabel[4], constraints);
        constraints.gridx = 4;
        menuPanel.add(startLabel[5], constraints);

        constraints.gridx = 0;
        constraints.gridy = 16;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(startTextField[3], constraints);
        constraints.gridx = 2;
        menuPanel.add(startTextField[4], constraints);
        constraints.gridx = 4;
        menuPanel.add(startTextField[5], constraints);

        constraints.gridx = 0;
        constraints.gridy = 17;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        menuPanel.add(rulesLabel[3], constraints);
        constraints.gridx = 2;
        menuPanel.add(rulesLabel[4], constraints);
        constraints.gridx = 4;
        menuPanel.add(rulesLabel[5], constraints);

        constraints.gridx = 0;
        constraints.gridy = 18;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        rulesTextArea[3].setLineWrap(true);
        menuPanel.add(new JScrollPane(rulesTextArea[3]), constraints);
        constraints.gridx = 2;;
        rulesTextArea[4].setLineWrap(true);
        menuPanel.add(new JScrollPane(rulesTextArea[4]), constraints);
        constraints.gridx = 4;
        rulesTextArea[5].setLineWrap(true);
        menuPanel.add(new JScrollPane(rulesTextArea[5]), constraints);

        constraints.gridx = 0;
        constraints.gridy = 19;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        menuPanel.add(iterationsLabel[3], constraints);
        constraints.gridx = 2;
        menuPanel.add(iterationsLabel[4], constraints);
        constraints.gridx = 4;
        menuPanel.add(iterationsLabel[5], constraints);

        constraints.gridx = 0;
        constraints.gridy = 20;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(iterationsTextField[3], constraints);
        constraints.gridx = 2;
        menuPanel.add(iterationsTextField[4], constraints);
        constraints.gridx = 4;
        menuPanel.add(iterationsTextField[5], constraints);

        constraints.gridx = 0;
        constraints.gridy = 21;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 2;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 4;
        menuPanel.add(new JSeparator(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 22;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        menuPanel.add(angleLabel[3], constraints);
        constraints.gridx = 2;
        menuPanel.add(angleLabel[4], constraints);
        constraints.gridx = 4;
        menuPanel.add(angleLabel[5], constraints);

        constraints.gridx = 0;
        constraints.gridy = 23;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(angleTextField[3], constraints);
        constraints.gridx = 2;
        menuPanel.add(angleTextField[4], constraints);
        constraints.gridx = 4;
        menuPanel.add(angleTextField[5], constraints);

        constraints.gridx = 0;
        constraints.gridy = 24;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        menuPanel.add(lineLabel[3], constraints);
        constraints.gridx = 2;
        menuPanel.add(lineLabel[4], constraints);
        constraints.gridx = 4;
        menuPanel.add(lineLabel[5], constraints);

        constraints.gridx = 0;
        constraints.gridy = 25;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(lineTextField[3], constraints);
        constraints.gridx = 2;
        menuPanel.add(lineTextField[4], constraints);
        constraints.gridx = 4;
        menuPanel.add(lineTextField[5], constraints);

        constraints.gridx = 0;
        constraints.gridy = 26;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 2;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 4;
        menuPanel.add(new JSeparator(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 27;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        menuPanel.add(originLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 28;
        menuPanel.add(cornerButton, constraints);

        constraints.gridx = 2;
        menuPanel.add(centerButton, constraints);

        constraints.gridx = 4;
        menuPanel.add(bottomButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 29;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 2;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 4;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(new JSeparator(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 30;
        menuPanel.add(verticalCheckBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 31;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 2;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 4;
        menuPanel.add(new JSeparator(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 32;
        menuPanel.add(randomCheckBox, constraints);

        constraints.gridx = 2;
        menuPanel.add(ModifyCheckBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 33;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 2;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 4;
        menuPanel.add(new JSeparator(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 34;
        menuPanel.add(fitnessLable[0], constraints);
        constraints.gridx = 2;
        menuPanel.add(fitnessLable[1], constraints);
        constraints.gridx = 4;
        menuPanel.add(fitnessLable[2], constraints);

        constraints.gridx = 0;
        constraints.gridy = 35;
        menuPanel.add(fitness[0], constraints);
        constraints.gridx = 2;
        menuPanel.add(fitness[1], constraints);
        constraints.gridx = 4;
        menuPanel.add(fitness[2], constraints);

        constraints.gridx = 0;
        constraints.gridy = 36;
        menuPanel.add(fitnessLable[3], constraints);
        constraints.gridx = 2;
        menuPanel.add(fitnessLable[4], constraints);
        constraints.gridx = 4;
        menuPanel.add(fitnessLable[5], constraints);

        constraints.gridx = 0;
        constraints.gridy = 37;
        menuPanel.add(fitness[3], constraints);
        constraints.gridx = 2;
        menuPanel.add(fitness[4], constraints);
        constraints.gridx = 4;
        menuPanel.add(fitness[5], constraints);

        constraints.gridx = 0;
        constraints.gridy = 38;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 2;
        menuPanel.add(new JSeparator(), constraints);
        constraints.gridx = 4;
        menuPanel.add(new JSeparator(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 39;
        menuPanel.add(GenLable, constraints);

        constraints.gridx = 2;
        constraints.gridwidth = 1;
        constraints.gridy = 39;
        menuPanel.add(GenTextField, constraints);

        constraints.gridx = 2;
        constraints.gridy = 40;
        drawButton.addActionListener(this);
        menuPanel.add(drawButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 41;
        exportButton.addActionListener(this);
        menuPanel.add(exportButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 42;
        importButton.addActionListener(this);
        menuPanel.add(importButton, constraints);

        GenTextField.setText("0");

        bottomButton.setSelected(true);
        verticalCheckBox.setSelected(true);

        startTextField[0].setText("F");
        rulesTextArea[0].setText("F:F[+F]F[-F]F\nG:G\nN:N\nX:X");
        iterationsTextField[0].setText("5");
        angleTextField[0].setText("25");
        lineTextField[0].setText("2");

        startTextField[1].setText("F");
        rulesTextArea[1].setText("F:F[+F]F[-F][F]\nG:G\nN:N\nX:X");
        iterationsTextField[1].setText("5");
        angleTextField[1].setText("20");
        lineTextField[1].setText("2");

        startTextField[2].setText("F");
        rulesTextArea[2].setText("F:FF-[-F+F+F]+[+F-F-F]\nG:G\nN:N\nX:X");
        iterationsTextField[2].setText("4");
        angleTextField[2].setText("22");
        lineTextField[2].setText("2");

        startTextField[3].setText("X");
        rulesTextArea[3].setText("F:FF\nG:G\nN:N\nX:F[+X]F[-X]+X");
        iterationsTextField[3].setText("7");
        angleTextField[3].setText("20");
        lineTextField[3].setText("2");

        startTextField[4].setText("X");
        rulesTextArea[4].setText("F:FF\nG:G\nN:N\nX:F[+X][-X]FX");
        iterationsTextField[4].setText("7");
        angleTextField[4].setText("25");
        lineTextField[4].setText("2");

        startTextField[5].setText("X");
        rulesTextArea[5].setText("F:FF\nG:G\nN:N\nX:F-[[X]+X]+F[+FX]-X");
        iterationsTextField[5].setText("5");
        angleTextField[5].setText("22");
        lineTextField[5].setText("2");
        for (int i = 0; i < frameSize; i++) {
            fitness[i].setText("1");
        }
    }

    /**
     * Returns the canvas object.
     *
     * @return The canvas.
     */
    public final Canvas getCanvas(int i) {
        return canvasPanel[i];
    }

    @Override
    public final void actionPerformed(final ActionEvent event) {
        if (event.getSource() == drawButton) {
            try {
                int startPosition = 1;
                if (!centerButton.isSelected()) {
                    startPosition = cornerButton.isSelected() ? 2 : 3;
                }
                if (!ModifyCheckBox.isSelected()) {
                    if (randomCheckBox.isSelected()) {
                        gen = 0;
                        pop = ga.initPopulation();
                        GenTextField.setText(Integer.toString(gen));
                        for (int i = 0; i < frameSize; i++) {
                            StringBuilder rulesString = new StringBuilder();
                            for (int j = 0; j < pop.getIndividual(i).getRulesNum(); j++) {
                                rulesString.append(pop.getIndividual(i).getChromosomeRulesIndex(j).toString() + "\n");
                            }
                            startTextField[i].setText(pop.getIndividual(i).getChromosomeAxiom().toString());
                            rulesTextArea[i].setText(rulesString.toString());
                            iterationsTextField[i].setText(Integer.toString(pop.getIndividual(i).getIterations()));
                            angleTextField[i].setText(Integer.toString((int) pop.getIndividual(i).getDelta()));
                            lineTextField[i].setText(Integer.toString(pop.getIndividual(i).getLineLength()));

                        }
                        LSystemWithGA.calculateLSystem(pop, startPosition,
                                verticalCheckBox.isSelected(), 0, 6);
                    } else {

                        if (end == 0) {
                            pop = ga.crossoverPopulation1(pop);
                            pop = ga.mutatePopulation(pop);
                            gen++;
                            ind = 0;
                            end = popsize / 6;
                        } else {
                            for (int i = ind * 6; i < (ind * 6) + 6; i++) {
                                StringBuilder rulesString = new StringBuilder();
                                for (int j = 0; j < pop.getIndividual(i).getRulesNum(); j++) {
                                    rulesString.append(pop.getIndividual(i).getChromosomeRulesIndex(j).toString() + "\n");
                                }
                                startTextField[i % 6].setText(pop.getIndividual(i).getChromosomeAxiom().toString());
                                rulesTextArea[i % 6].setText(rulesString.toString());
                                iterationsTextField[i % 6].setText(Integer.toString(pop.getIndividual(i).getIterations()));
                                angleTextField[i % 6].setText(Integer.toString((int) pop.getIndividual(i).getDelta()));
                                lineTextField[i % 6].setText(Integer.toString(pop.getIndividual(i).getLineLength()));

                            }
                            LSystemWithGA.calculateLSystem(pop, startPosition,
                                    verticalCheckBox.isSelected(), ind * 6, (ind * 6) + 6);
                            for (int i = ind * 6; i < (ind * 6) + 6; i++) {
                                pop.getIndividual(i).setChromosomeAxiom(startTextField[i % 6].getText().replaceAll("\\s", ""));
                                String[] rules = rulesTextArea[i % 6].getText().split("\\n");
                                pop.getIndividual(i).setChromosomeRules(rules, rules.length);
                                pop.getIndividual(i).setIterations(Integer.parseInt(iterationsTextField[i % 6].getText()));
                                pop.getIndividual(i).setDelta(Integer.parseInt(angleTextField[i % 6].getText()));
                                pop.getIndividual(i).setLineLength(Integer.parseInt(lineTextField[i % 6].getText()));
                            }
                            if (ind != 0) {
                                for (int i = (ind * 6); i < (ind * 6) + 6; i++) {
                                    pop.getIndividual(i - 6).setFitness(Integer.parseInt(fitness[i % 6].getText()));
                                }
                            } else {
                                for (int i = (ind * 6); i < (ind * 6) + 6; i++) {
                                    pop.getIndividual(i).setFitness(Integer.parseInt(fitness[i % 6].getText()));
                                }
                            }
                            ind++;
                            end--;
                        }

                        GenTextField.setText(Integer.toString(gen));
                    }
                } else {
                    if (ind != 0) {
                        ind--;
                        end++;
                    }
                    for (int i = ind * 6; i < (ind * 6) + 6; i++) {
                        pop.getIndividual(i).setChromosomeAxiom(startTextField[i % 6].getText().replaceAll("\\s", ""));
                        String[] rules = rulesTextArea[i % 6].getText().split("\\n");
                        pop.getIndividual(i).setChromosomeRules(rules, rules.length);
                        pop.getIndividual(i).setIterations(Integer.parseInt(iterationsTextField[i % 6].getText()));
                        pop.getIndividual(i).setDelta(Integer.parseInt(angleTextField[i % 6].getText()));
                        pop.getIndividual(i).setLineLength(Integer.parseInt(lineTextField[i % 6].getText()));
                    }
                    LSystemWithGA.calculateLSystem(pop, startPosition,
                            verticalCheckBox.isSelected(), ind * 6, (ind * 6) + 6);
                    ind++;
                    end--;
                }
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(canvasPanel[0],
                        "Please review input parameters.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (event.getSource() == exportButton) {
            this.file = new File("C:\\Users\\Alaa Aldin Hajjar\\Desktop\\out.txt");
            try {
                this.fw = new FileWriter(file);
            } catch (IOException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.pw = new PrintWriter(fw);
            for (int i = 0; i < popsize; i++) {
                pw.println(pop.getIndividual(i).getChromosomeAxiom().toString());
                pw.println(pop.getIndividual(i).getChromosomeRulesIndex(0).toString());
                pw.println(Integer.toString(pop.getIndividual(i).getIterations()));
                pw.println(Integer.toString((int) pop.getIndividual(i).getDelta()));
                pw.println(Integer.toString(pop.getIndividual(i).getLineLength()));
                pw.println(Integer.toString((int) pop.getIndividual(i).getFitness()));
            }
            pw.close();
        } else if (event.getSource() == importButton) {
            try {
                sc = new Scanner(new File("C:\\Users\\Alaa Aldin Hajjar\\Desktop\\out.txt"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int i = 0; i < popsize; i++) {
                pop.getIndividual(i).setChromosomeAxiom(sc.next());
                pop.getIndividual(i).setChromosomeRulesIndex(0, sc.next());
                pop.getIndividual(i).setIterations(Integer.parseInt(sc.next()));
                pop.getIndividual(i).setDelta(Integer.parseInt(sc.next()));
                pop.getIndividual(i).setLineLength(Integer.parseInt(sc.next()));
                pop.getIndividual(i).setFitness(Integer.parseInt(sc.next()));
            }
            sc.close();
        }

    }
}
