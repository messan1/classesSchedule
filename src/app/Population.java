package app;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

class Population {
    private int populationSize;
    private double populationFitness;
    private Individual[] individuals;

    public Population(int populationSize) {
        this.individuals = new Individual[populationSize];
    }

    public Population(Timetable timetable, int populationSize) {
        this.populationSize = populationSize;
        this.individuals = new Individual[this.populationSize];
        for (int index = 0; index < this.populationSize; index++) {
            Individual individual = new Individual(timetable);
            this.individuals[index] = individual;
           
        }
    }

    public int size() {
        return this.individuals.length;
    }

    public void setPopulationFitness(double fitness) {
        this.populationFitness = fitness;
    }

    public double getPopulationFitness() {
        return this.populationFitness;
    }

    public Individual[] getIndividuals() {
        return this.individuals;
    }

    public Individual setIndividual(int offset, Individual individual) {
		return this.individuals[offset] = individual;
	}
    public Individual getIndividual(int offset) {
        return this.individuals[offset];
    }

    public Individual getFittest(int offset) {
        Arrays.sort(this.individuals, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if (o1.getFitness() > o2.getFitness()) {
                    return -1;
                } else if (o1.getFitness() < o2.getFitness()) {
                    return 1;
                }
                return 0;
            }
        });
        return this.individuals[offset];
    }

    public void shuffle() {
        Random rnd = new Random();
        for (int i = this.individuals.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Individual a = this.individuals[index];
            this.individuals[index] = this.individuals[i];
            this.individuals[i] = a;
        }
    }
}