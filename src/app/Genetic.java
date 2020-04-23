package app;

public class Genetic {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
    protected int tournamentSize;

    public Genetic(int populationSize, double mutationRate, double crossoverRate, int elitismCount,
            int tournamentSize) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.tournamentSize = tournamentSize;
        this.elitismCount = elitismCount;
    }

    public Population initPopulation(Timetable timetable) {
        Population population = new Population(timetable, this.populationSize);
        return population;
    }


    public double calcFitness(Individual individual, Timetable timetable) {
        Timetable threadTimetable = new Timetable(timetable);
        threadTimetable.createClasses(individual);
        int clash = threadTimetable.calcClashes();
        double fitness = 1 / (double) (1 + clash);
        individual.setFitness(fitness);
        return fitness;
    }

    public void evalutePopulation(Population population, Timetable timetable) {
        double populationFitness = 0;
        for (Individual individual : population.getIndividuals()) {
            populationFitness += this.calcFitness(individual, timetable);
        }
        population.setPopulationFitness(populationFitness);
    }

    public Individual selectParent(Population population) {
        Population tounament = new Population(this.tournamentSize);
        population.shuffle();
        for (int index = 0; index < this.tournamentSize; index++) {
            Individual tounamentIndividial = population.getIndividual(index);
            tounament.setIndividual(index, tounamentIndividial);
        }
        return tounament.getFittest(0);
    }

    public Population crossoverPopulation(Population population) {
        Population newPopulation = new Population(population.size());

        for (int index = 0; index < population.size(); index++) {
            Individual parent1 = population.getFittest(index);

            if (this.crossoverRate > Math.random() && index >= this.elitismCount) {

                Individual offspring = new Individual(parent1.getChromosomeLenght());

                Individual parent2 = selectParent(population);

                for (int gene = 0; gene < parent1.getChromosomeLenght(); gene++) {
                    if (0.5 > Math.random()) {

                        offspring.setGene(gene, parent1.getGene(gene));
                    } else {
                        
                        offspring.setGene(gene, parent2.getGene(gene));
                    }
                }
                newPopulation.setIndividual(index, offspring);
            } else {
                newPopulation.setIndividual(index, parent1);
            }
        }
        return newPopulation;
    }

    public Population mutationPopulation(Population population, Timetable timetable) {
        Population newPopulation = new Population(this.populationSize);
        for (int index = 0; index < population.size(); index++) {
            Individual individual = population.getFittest(index);
            Individual randomIndividual = new Individual(timetable);
            for (int gene = 0; gene < individual.getChromosomeLenght(); gene++) {
                if (index >= this.elitismCount) {
                    if (this.mutationRate > Math.random()) {
                        individual.setGene(gene, randomIndividual.getGene(gene));
                    }
                }
            }
            newPopulation.setIndividual(index, individual);
        }
        return newPopulation;
    }
    public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
		return (generationsCount > maxGenerations);
	}

	public boolean isTerminationConditionMet(Population population) {
		return population.getFittest(0).getFitness() == 1.0;
	}
}