package l.system.with.ga;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An "Individual" represents a single candidate solution.
 */
public final class Individual {

    private int rulesNum = 1;
    private double fitness = -1;
    private String chromosomeAxiom = new String();
    private String[] chromosomeRules = new String[rulesNum];
    private int delta = 0;
    private int lineLength = 0;
    private int Iterations = 0;
    private int[][] color = new int[3][3];

    /**
     * Initializes individual with specific chromosome
     *
     * @param chromosome The chromosome to give individual
     */
    /**
     * Initializes random individual.
     */
    public Individual() {
        //Initializes the axiom of the individual in a random way
        axiomSet();
        //Initializes the rules of the individual in a random way
        for (int i = 0; i < rulesNum; i++) {
            ruleSet(i);
        }
        //Initializes the delta of the individual in a random way
        setDelta((int) ((ThreadLocalRandom.current().nextDouble() * 89) + 1));
        //Initializes the Iterations of the individual in a random way
        setIterations((int) (ThreadLocalRandom.current().nextDouble() * 6) + 2);
        //Initializes the line length of the individual in a random way
        setLineLength((int) (ThreadLocalRandom.current().nextDouble() * 30) + 9);
        //Initializes the color of the individual in a random way
        for (int i = 0; i < color.length; i++) {
            for (int j = 0; j < color[0].length; j++) {
                color[i][j] = (int) (ThreadLocalRandom.current().nextDouble() * 255);
            }
        }
    }

    /**
     * Store individual's fitness
     *
     * @param fitness The individuals fitness
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * Gets individual's fitness
     *
     * @return The individual's fitness
     */
    public double getFitness() {
        return this.fitness;
    }

    public void axiomSet() {
        
        int axiomLenth = (int) ((ThreadLocalRandom.current().nextDouble() * 9) + 1);
        double random;
        boolean tt = false;
        StringBuilder tempAxiom = new StringBuilder();
        for (int i = 0; i < axiomLenth; i++) {
            random = ThreadLocalRandom.current().nextDouble() * 100;
            if (random < 50) {
                tempAxiom.append("F");
                tt = true;
            }
            else {
                if (random < 75) {
                    tempAxiom.append("+");
                } else {
                    tempAxiom.append("-");
                }
            }
        }
        if (!tt) {
            tempAxiom.append("F");
        }
        chromosomeAxiom = tempAxiom.toString();
    }

    public void ruleSet(int i) {
        double random = 0;
        int numOpen = 0;
        boolean t = false;
        int ruleLength = (int) ((ThreadLocalRandom.current().nextDouble()) * 10) + 1;
        StringBuilder tempRule = new StringBuilder();
        for (int j = 0; j < ruleLength; j++) {
            random = ThreadLocalRandom.current().nextDouble() * 100;
            if (random < 30) {
                tempRule.append("F");
                t = true;
            }
            else {
                if (random < 65) {
                    tempRule.append("+");
                } else {
                    tempRule.append("-");
                }
            }
        }
        if (!t) {
            int tempIndex = tempRule.length();
            tempRule.append("F");
            tempRule.insert((int) (ThreadLocalRandom.current().nextDouble() * tempIndex), "F");
        }
        if (i == 0) {
            tempRule.insert(0, "F:");
        }
        chromosomeRules[i] = new String(tempRule.toString());
    }

    public void setColor(int[][] color) {
        this.color = color;
    }

    public void setColor(int i, int[] color) {
        this.color[i] = color;
    }

    public void setColor(int i, int j, int color) {
        this.color[i][j] = color;
    }

    public void setChromosomeRules(String[] chromosomeRulesString, int i) {
        this.chromosomeRules = new String[i];
        this.chromosomeRules = chromosomeRulesString;
    }

    public void setChromosomeAxiom(String chromosomeAxiom) {
        this.chromosomeAxiom = chromosomeAxiom;
    }

    public void setChromosomeAxiomAppend(String AxiomI) {
        StringBuilder temp = new StringBuilder(chromosomeAxiom);
        temp.append(AxiomI);
        this.chromosomeAxiom = temp.toString();
    }

    public void setChromosomeAxiomInsert(int i, String AxiomI) {
        StringBuilder temp = new StringBuilder(chromosomeAxiom);
        temp.insert(i, AxiomI);
        this.chromosomeAxiom = temp.toString();
    }

    public void setChromosomeRuleIndexInsert(int rule, int i, String ruleI) {
        StringBuilder temp = new StringBuilder(chromosomeRules[rule]);
        temp.insert(i, ruleI);
        this.chromosomeRules[rule] = temp.toString();
    }

    public void setChromosomeAxiomIndex(int i, String replace) {
        StringBuilder temp = new StringBuilder(chromosomeAxiom);
        temp.replace(i, i + 1, replace);
        this.chromosomeAxiom = temp.toString();
    }

    public void setChromosomeRulesIndex(int i, String replace) {
        this.chromosomeRules[i] = replace;
    }

    public void setChromosomeRulesIndexAppend(int i, String ruleI) {
        StringBuilder temp = new StringBuilder(chromosomeRules[i]);
        temp.append(ruleI);
        this.chromosomeRules[i] = temp.toString();
    }

    public void setChromosomeRules2Index(int rule, int i, String replace) {
        StringBuilder temp = new StringBuilder(chromosomeRules[rule]);
        temp.replace(i, i + 1, replace);
        this.chromosomeRules[rule] = temp.toString();
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public void setIterations(int Iterations) {
        this.Iterations = Iterations;
    }

    public void setLineLength(int lineLength) {
        this.lineLength = lineLength;
    }

    public void setRulesNum(int rulesNum) {
        this.rulesNum = rulesNum;
    }

    public int[][] getColor() {
        return color;
    }

    public int[] getColor(int i) {
        return color[i];
    }

    public String getChromosomeAxiom() {
        return chromosomeAxiom;
    }

    public char getChromosomeAxiom(int i) {
        return chromosomeAxiom.charAt(i);
    }

    public String[] getChromosomeRules() {
        return chromosomeRules;
    }

    public String getChromosomeRulesIndex(int i) {
        return chromosomeRules[i];
    }

    public char getChromosomeRules2Index(int i, int j) {
        return chromosomeRules[i].charAt(j);
    }

    public int getDelta() {
        return delta;
    }

    public int getIterations() {
        return Iterations;
    }

    public int getLineLength() {
        return lineLength;
    }

    public int getRulesNum() {
        return rulesNum;
    }
}
