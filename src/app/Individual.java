package app;

public class Individual {
    private int[] chromosomes;
    private double fitness;

    public Individual(int chromosomeLengh) {
        this.chromosomes = new int[chromosomeLengh];
    }

    public Individual(Timetable timetable) {
        int numClasses = timetable.getNumClasses();
        int chromosomeLengh = numClasses * 5;
        int[] individual = new int[chromosomeLengh];
        int chromosomePos = 0;
        for (Group group : timetable.getGroupsAsArray()) {
            for (int moduleId : group.moduleIds()) {

                int randomDay = (int) (Math.random()*5);
                individual[chromosomePos] = randomDay;
                chromosomePos++;

                individual[chromosomePos] = timetable.getRandomRoom().getroomId();
                chromosomePos++;

                Module module = timetable.getModule(moduleId);
                individual[chromosomePos] = module.getRandomProfessor();
                chromosomePos++;


                int startHours = timetable.getRandomTimeslot().getStartHours();
                individual[chromosomePos] = startHours;
                chromosomePos++;


            }
        }
        this.chromosomes = individual;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return this.fitness;
    }

    public int[] getIndividuals() {
        return this.chromosomes;
    }

    public int getChromosomeLenght() {
        return this.chromosomes.length;
    }

    public int getGene(int offset) {
        return this.chromosomes[offset];
    }

    public void setGene(int offset, int gene) {
        this.chromosomes[offset] = gene;
    }
}