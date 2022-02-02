package l.system.with.ga;

import gui.Frame;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LSystemWithGA {

    private static Frame frame;
    private static Turtle[] turtle = new Turtle[6];

    public static void main(String[] args) throws IOException {

        frame = new Frame();
        for (int i = 0; i < 6; i++) {
            turtle[i] = new Turtle(frame.getCanvas(i));
        }
    }

    public static void calculateLSystem(Population pop, final int startPosition,
            final boolean vertical, int start, int end) {
        for (int i = start; i < end; i++) {
            String state = pop.getIndividual(i).getChromosomeAxiom().toString();
            Map<Character, char[]> parsedRules = parseRules(pop.getIndividual(i).getChromosomeRules());
            for (int j = 0; j < pop.getIndividual(i).getIterations(); j++) {
                state = applyRules(parsedRules, state);
            }
            turtle[i%6].setProperties(state, (int) pop.getIndividual(i).getDelta(), pop.getIndividual(i).getLineLength(), startPosition, vertical, pop.getIndividual(i).getColor());
            turtle[i%6].draw();
        }
    }

    /**
     * Parses user input and returns a map of rules to apply.
     *
     * @param rules Rules input.
     * @return Map of productions.
     */
    private static Map<Character, char[]> parseRules(final String[] rules) {
        Map<Character, char[]> parsedRules = new HashMap<Character, char[]>();

        for (String rule : rules) {
            rule = rule.replaceAll("[:\\s]", "");
            char[] parsedRule = rule.toCharArray();
            char constant = parsedRule[0];
            char[] production = Arrays.copyOfRange(parsedRule, 1,
                    parsedRule.length);
            parsedRules.put(constant, production);

        }

        return parsedRules;
    }

    /**
     * Calculates another iteration of the system.
     *
     * @param rules System rules.
     * @param state Current state of the system.
     * @return Next iteration of the system.
     */
    private static String applyRules(final Map<Character, char[]> rules,
            final String state) {
        String newState = "";
        for (char item : state.toCharArray()) {
            if (rules.get(item) != null) {
                String p = new String(rules.get(item));
                newState += p;
            } else {
                newState += item;
            }
        }

        return newState;
    }

    /**
     * Empty constructor.
     */
    private LSystemWithGA() {

    }

}
