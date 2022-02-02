package l.system.with.ga;

import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {

    private int populationSize;

    private double mutationRate;

    private double crossoverRate;

    private int elitismCount;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
    }

    /**
     * Initialize population
     *
     */
    public Population initPopulation() {
        // Initialize population
        Population population = new Population(this.populationSize);
        return population;
    }

    /**
     * Select parent for crossover
     */

    /**
     * Apply crossover to population
     */
    public Population crossoverPopulation1(Population population) {
        // Create new population
        Population newPopulation = new Population(population.size());

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            // Apply crossover to this individual?
            if (this.crossoverRate > ThreadLocalRandom.current().nextDouble() && populationIndex >= this.elitismCount) {
                // Initialize offspring
                Individual offspring = new Individual();

                // Find second parent
                Individual parent2 = population.getIndividual((int) (ThreadLocalRandom.current().nextDouble() * (populationSize - 1)));
                // Loop over genome
                if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                    if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                        offspring.setChromosomeAxiom(parent1.getChromosomeAxiom());
                    } else {
                        offspring.setChromosomeAxiom(parent2.getChromosomeAxiom());
                    }
                } else {
                    StringBuilder tempAxiom = new StringBuilder();
                    if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                        tempAxiom.append(parent1.getChromosomeAxiom().substring(0, parent1.getChromosomeAxiom().length() / 2));
                        tempAxiom.append(parent2.getChromosomeAxiom().substring(parent2.getChromosomeAxiom().length() / 2));
                    } else {
                        tempAxiom.append(parent2.getChromosomeAxiom().substring(0, parent2.getChromosomeAxiom().length() / 2));
                        tempAxiom.append(parent1.getChromosomeAxiom().substring(parent1.getChromosomeAxiom().length() / 2));
                    }
                    boolean find = false;
                    for (int i = 0; i < tempAxiom.length(); i++) {
                        if (tempAxiom.charAt(i) == 'F') {
                            find = true;
                            break;
                        }
                    }
                    if (!find) {
                        offspring.axiomSet();
                    } else {
                        offspring.setChromosomeAxiom(tempAxiom.toString());
                    }
                }
                if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                    for (int i = 0; i < offspring.getRulesNum(); i++) {
                        if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                            offspring.setChromosomeRulesIndex(i, parent1.getChromosomeRulesIndex(i));
                        } else {
                            offspring.setChromosomeRulesIndex(i, parent2.getChromosomeRulesIndex(i));
                        }
                    }
                } else {
                    for (int i = 0; i < offspring.getRulesNum(); i++) {
                        StringBuilder tempRule = new StringBuilder();
                        if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                            tempRule.append(parent1.getChromosomeRulesIndex(i).substring(0, parent1.getChromosomeRulesIndex(i).length() / 2));
                            tempRule.append(parent2.getChromosomeRulesIndex(i).substring(parent2.getChromosomeRulesIndex(i).length() / 2));
                        } else {
                            tempRule.append(parent2.getChromosomeRulesIndex(i).substring(0, parent2.getChromosomeRulesIndex(i).length() / 2));
                            tempRule.append(parent1.getChromosomeRulesIndex(i).substring(parent1.getChromosomeRulesIndex(i).length() / 2));
                        }
                        boolean find = false;
                        for (int j = 0; j < tempRule.length(); j++) {
                            if (tempRule.charAt(j) == 'F') {
                                find = true;
                                break;
                            }
                        }
                        if (!find) {
                            offspring.ruleSet(i);
                        } else {
                            offspring.setChromosomeRulesIndex(i, tempRule.toString());
                        }
                    }

                }
                if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                    offspring.setDelta((int) ((ThreadLocalRandom.current().nextDouble() * (Math.max(parent1.getDelta(), parent2.getDelta()) - Math.min(parent1.getDelta(), parent2.getDelta())) + Math.min(parent1.getDelta(), parent2.getDelta()))));
                } else {
                    if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                        offspring.setDelta(parent1.getDelta());
                    } else {
                        offspring.setDelta(parent2.getDelta());
                    }
                }
                if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                    offspring.setLineLength((int) ((ThreadLocalRandom.current().nextDouble() * (Math.max(parent1.getLineLength(), parent2.getLineLength()) - Math.min(parent1.getLineLength(), parent2.getLineLength())) + Math.min(parent1.getLineLength(), parent2.getLineLength()))));
                } else {
                    if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                        offspring.setLineLength(parent1.getLineLength());
                    } else {
                        offspring.setLineLength(parent2.getLineLength());
                    }
                }
                if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                    offspring.setIterations((int) ((ThreadLocalRandom.current().nextDouble() * (Math.max(parent1.getIterations(), parent2.getIterations()) - Math.min(parent1.getIterations(), parent2.getIterations())) + Math.min(parent1.getIterations(), parent2.getIterations()))));

                } else {
                    if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                        offspring.setIterations(parent1.getIterations());
                    } else {
                        offspring.setIterations(parent2.getIterations());
                    }
                }
                for (int i = 0; i < offspring.getColor().length; i++) {
                    if (ThreadLocalRandom.current().nextDouble() > 0.5) {
                        offspring.setColor(i, parent1.getColor(i));
                    } else {
                        offspring.setColor(i, parent2.getColor(i));
                    }
                }
                // Add offspring to new population
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                // Add individual to new population without applying crossover
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }

        return newPopulation;
    }

    /**
     * Apply mutation to population
     */
    public Population mutatePopulation(Population population) {
        // Initialize new population
        Population newPopulation = new Population(this.populationSize);

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);
            if (populationIndex > this.elitismCount) {
                // Does this gene need mutation?
                if (this.mutationRate > ThreadLocalRandom.current().nextDouble()) {

                    // Loop over individual's genes
                    if (ThreadLocalRandom.current().nextDouble() < 0.166) {
                        individual.axiomSet();
                    } else {
                        if (ThreadLocalRandom.current().nextDouble() < 0.332) {

                            individual.ruleSet(0);
                        } else {
                            if (ThreadLocalRandom.current().nextDouble() < 0.498) {
                                individual.setDelta((int) ((ThreadLocalRandom.current().nextDouble() * 89) + 1));
                            } else {
                                if (ThreadLocalRandom.current().nextDouble() < 0.664) {
                                    individual.setIterations((int) (ThreadLocalRandom.current().nextDouble() * 6) + 2);
                                } else {
                                    if (ThreadLocalRandom.current().nextDouble() < 0.83) {
                                        individual.setLineLength((int) (ThreadLocalRandom.current().nextDouble() * 30) + 9);
                                    } else {
                                        for (int i = 0; i < individual.getColor().length; i++) {
                                            for (int j = 0; j < individual.getColor()[0].length; j++) {
                                                individual.setColor(i, j, (int) (ThreadLocalRandom.current().nextDouble() * 255));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Add individual to population
            newPopulation.setIndividual(populationIndex, individual);
        }

        // Return mutated population
        return newPopulation;
    }

}
