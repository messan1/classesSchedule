package app;

public class App {
    public static void main(String[] args) throws Exception {
        Timetable timetable = new Timetable();
        // Groupes
        timetable.addGroup(1, "6eme", 25, new int[] { 1, 2, 3 });
        timetable.addGroup(2, "5eme", 30, new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        timetable.addGroup(3, "3eme", 30, new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        timetable.addGroup(4, "2nd", 54, new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        timetable.addGroup(5, "1ere", 10, new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        timetable.addGroup(6, "tle", 90, new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        timetable.addGroup(7, "tlec", 30, new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        // teachers
        timetable.addProfessor(1, "Messan");
        timetable.addProfessor(2, "Franck");
        timetable.addProfessor(3, "Johny");
        timetable.addProfessor(4, "Ruby");
        timetable.addProfessor(5, "Kromor");
        timetable.addProfessor(6, "Leonce");
        timetable.addProfessor(7, "Assena");
        timetable.addProfessor(8, "elo");
        timetable.addProfessor(9, "Nadia");

        // timeslots
        timetable.addTimeslot(1, "1", 1);
        timetable.addTimeslot(2, "2", 2);
        timetable.addTimeslot(3, "3", 3);
        timetable.addTimeslot(4, "4", 4);
        timetable.addTimeslot(5, "5", 5);
        timetable.addTimeslot(6, "6", 6);
        timetable.addTimeslot(7, "7", 7);
        timetable.addTimeslot(8, "8", 8);
        timetable.addTimeslot(9, "9", 9);
        timetable.addTimeslot(10, "10", 10);
        timetable.addTimeslot(11, "11", 11);
        timetable.addTimeslot(12, "12", 12);
        timetable.addTimeslot(13, "13", 13);
        timetable.addTimeslot(14, "14", 14);
        timetable.addTimeslot(15, "15", 15);
        timetable.addTimeslot(17, "17", 17);
        timetable.addTimeslot(18, "18", 18);
        timetable.addTimeslot(19, "1", 19);

        // rooms
        timetable.addRoom(0, "B1", 100);
        timetable.addRoom(1, "B2", 50);
        timetable.addRoom(2, "B3", 40);
        timetable.addRoom(3, "B4", 20);
        timetable.addRoom(4, "B5", 10);
        timetable.addRoom(5, "B3", 40);
        timetable.addRoom(6, "B4", 20);
        timetable.addRoom(7, "B5", 10);
        timetable.addRoom(8, "B3", 40);
        timetable.addRoom(9, "B4", 20);
        timetable.addRoom(10, "B5", 10);
        timetable.addRoom(11, "B3", 40);
        timetable.addRoom(12, "B4", 20);
        timetable.addRoom(13, "B5", 10);
        timetable.addRoom(14, "B5", 10);
        timetable.addRoom(15, "B5", 10);

        // Modules
        timetable.addModule(1, "Math", new int[] { 1, 5, 6 }, 2);
        timetable.addModule(2, "Fr", new int[] { 6, 7, 1 }, 3);
        timetable.addModule(3, "Ang", new int[] { 3, 2 }, 4);
        timetable.addModule(4, "Philo", new int[] { 5, 1 }, 1);
        timetable.addModule(5, "esp", new int[] { 1, 9, 8 }, 1);
        timetable.addModule(6, "algo", new int[] { 9, 5, 6 }, 2);
        timetable.addModule(7, "electr", new int[] { 5 }, 3);
        timetable.addModule(8, "svt", new int[] { 8 }, 1);

        int generation = 1;
        Genetic GA = new Genetic(200, 0.01, 0.9, 2, 5);

        Population population = GA.initPopulation(timetable);

        GA.evalutePopulation(population, timetable);

        while (GA.isTerminationConditionMet(generation, 1000) == false
                && GA.isTerminationConditionMet(population) == false) {
            System.out.println("G" + generation + " Best fitness: " + population.getFittest(0).getFitness());

            population = GA.crossoverPopulation(population);

            population = GA.mutationPopulation(population, timetable);

            GA.evalutePopulation(population, timetable);

            generation++;

        }
        timetable.createClasses(population.getFittest(0),timetable);
        System.out.println();
        System.out.println("Solution found in " + generation + " generations");
        System.out.println("Final solution fitness: " + population.getFittest(0).getFitness());
        System.out.println("Clashes: " + timetable.calcClashes());

        // Print classes
        System.out.println();
        Class classes[] = timetable.getClasses();
        int classIndex = 1;
        for (Class bestClass : classes) {
            System.out.println("Class " + classIndex + ":");
            System.out.println("Module: " + timetable.getModule(bestClass.getModuleID()).getModuleCode());
            System.out.println("Group: " + timetable.getGroup(bestClass.getGroupID()).getGroupId());
            System.out.println("Room: " + timetable.getRoom(bestClass.getRoomID()).getroomName());
            System.out.println("Professor: " + timetable.getProfessor(bestClass.getProfessorID()).getprofessorName());
            System.out.println("Day: " + bestClass.getDay());
            System.out.println("Start Time: " + bestClass.getStartHours());
            System.out.println("Finsih Time: " + bestClass.getfinishHours());
            System.out.println("-----");
            classIndex++;
        }
    }
}
