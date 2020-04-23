package app;

import java.util.HashMap;

public class Timetable {
	private final HashMap<Integer, Room> rooms;
	private final HashMap<Integer, Professor> professors;
	private final HashMap<Integer, Module> modules;
	private final HashMap<Integer, Group> groups;
	private final HashMap<Integer, Timeslot> timeslots;
	private Class classes[];

	private int numClasses = 0;

	/**
	 * Initialize new Timetable
	 */
	public Timetable() {
		this.rooms = new HashMap<Integer, Room>();
		this.professors = new HashMap<Integer, Professor>();
		this.modules = new HashMap<Integer, Module>();
		this.groups = new HashMap<Integer, Group>();
		this.timeslots = new HashMap<Integer, Timeslot>();
	}

	public Timetable(Timetable cloneable) {
		this.rooms = cloneable.getRooms();
		this.professors = cloneable.getProfessors();
		this.modules = cloneable.getModules();
		this.groups = cloneable.getGroups();
		this.timeslots = cloneable.getTimeslots();
	}

	private HashMap<Integer, Group> getGroups() {
		return this.groups;
	}

	private HashMap<Integer, Timeslot> getTimeslots() {
		return this.timeslots;
	}

	private HashMap<Integer, Module> getModules() {
		return this.modules;
	}

	private HashMap<Integer, Professor> getProfessors() {
		return this.professors;
	}

	public void addRoom(int roomId, String roomName, int capacity) {
		this.rooms.put(roomId, new Room(roomId, roomName, capacity));
	}

	public void addProfessor(int professorId, String professorName) {
		this.professors.put(professorId, new Professor(professorId, professorName));
	}

	public void addModule(int moduleId, String moduleCode, int professorIds[], int hours) {
		this.modules.put(moduleId, new Module(moduleId, moduleCode, professorIds, hours));
	}

	public void addGroup(int groupId, String goupName, int groupSize, int moduleIds[]) {
		this.groups.put(groupId, new Group(groupId, goupName, groupSize, moduleIds));
		this.numClasses = 0;
	}

	public void addTimeslot(int timeslotId, String timeslot, int starthours) {
		this.timeslots.put(timeslotId, new Timeslot(timeslotId, timeslot, starthours));
	}

	public void createClasses(Individual individual, Timetable timetable) {
		// Init classes
		Class classes[] = new Class[this.getNumClasses()];

		// Get individual's chromosome
		int chromosome[] = individual.getIndividuals();
		int chromosomePos = 0;
		int classIndex = 0;

		for (Group group : this.getGroupsAsArray()) {
			int moduleIds[] = group.getModuleIds();
			for (int moduleId : moduleIds) {
				classes[classIndex] = new Class(classIndex, group.getGroupId(), moduleId);

				classes[classIndex].addDay(chromosome[chromosomePos]);
				chromosomePos++;

				// Add room
				classes[classIndex].addRoomID(chromosome[chromosomePos]);
				chromosomePos++;

				// Add professor
				classes[classIndex].addProfessorID(chromosome[chromosomePos]);
				chromosomePos++;

				// Add timeslot
				int starthours = chromosome[chromosomePos];
				classes[classIndex].addStartHours(chromosome[chromosomePos]);
				chromosomePos++;

				int hours = timetable.getModule(moduleId).getModuleHours();
				classes[classIndex].addfinishHours(starthours + hours);
				classIndex++;
			}
		}

		this.classes = classes;
	}

	/**
	 * Get room from roomId
	 * 
	 * @param roomId
	 * @return room
	 */
	public Room getRoom(int roomId) {
		if (!this.rooms.containsKey(roomId)) {
			System.out.println("Rooms doesn't contain key " + roomId);
		}
		return (Room) this.rooms.get(roomId);
	}

	public HashMap<Integer, Room> getRooms() {
		return this.rooms;
	}

	/**
	 * Get random room
	 * 
	 * @return room
	 */
	public Room getRandomRoom() {
		Object[] roomsArray = this.rooms.values().toArray();
		Room room = (Room) roomsArray[(int) (roomsArray.length * Math.random())];
		return room;
	}

	/**
	 * Get professor from professorId
	 * 
	 * @param professorId
	 * @return professor
	 */
	public Professor getProfessor(int professorId) {
		return (Professor) this.professors.get(professorId);
	}

	/**
	 * Get module from moduleId
	 * 
	 * @param moduleId
	 * @return module
	 */
	public Module getModule(int moduleId) {
		return (Module) this.modules.get(moduleId);
	}

	/**
	 * Get moduleIds of student group
	 * 
	 * @param groupId
	 * @return moduleId array
	 */
	public int[] getGroupModules(int groupId) {
		Group group = (Group) this.groups.get(groupId);
		return group.getModuleIds();
	}

	/**
	 * Get group from groupId
	 * 
	 * @param groupId
	 * @return group
	 */
	public Group getGroup(int groupId) {
		return (Group) this.groups.get(groupId);
	}

	/**
	 * Get all student groups
	 * 
	 * @return array of groups
	 */
	public Group[] getGroupsAsArray() {
		return (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
	}

	/**
	 * Get timeslot by timeslotId
	 * 
	 * @param timeslotId
	 * @return timeslot
	 */
	public Timeslot getTimeslot(int timeslotId) {
		return (Timeslot) this.timeslots.get(timeslotId);
	}

	/**
	 * Get random timeslotId
	 * 
	 * @return timeslot
	 */
	public Timeslot getRandomTimeslot() {
		Object[] timeslotArray = this.timeslots.values().toArray();
		Timeslot timeslot = (Timeslot) timeslotArray[(int) (timeslotArray.length * Math.random())];
		return timeslot;
	}

	/**
	 * Get classes
	 * 
	 * @return classes
	 */
	public Class[] getClasses() {
		return this.classes;
	}

	/**
	 * Get number of classes that need scheduling
	 * 
	 * @return numClasses
	 */
	public int getNumClasses() {
		if (this.numClasses > 0) {
			return this.numClasses;
		}

		int numClasses = 0;
		Group groups[] = (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
		for (Group group : groups) {
			numClasses += group.getModuleIds().length;
		}
		this.numClasses = numClasses;

		return this.numClasses;
	}

	/**
	 * Calculate the number of clashes between Classes generated by a chromosome.
	 * 
	 * The most important method in this class; look at a candidate timetable and
	 * figure out how many constraints are violated.
	 * 
	 * Running this method requires that createClasses has been run first (in order
	 * to populate this.classes). The return value of this method is simply the
	 * number of constraint violations (conflicting professors, timeslots, or
	 * rooms), and that return value is used by the GeneticAlgorithm.calcFitness
	 * method.
	 * 
	 * There's nothing too difficult here either -- loop through this.classes, and
	 * check constraints against the rest of the this.classes.
	 * 
	 * The two inner `for` loops can be combined here as an optimization, but kept
	 * separate for clarity. For small values of this.classes.length it doesn't make
	 * a difference, but for larger values it certainly does.
	 * 
	 * @return numClashes
	 */
	public int calcClashes() {
		int clashes = 0;

		for (Class classA : this.classes) {
			// Check room capacity
			int roomCapacity = this.getRoom(classA.getRoomID()).getcapacity();
			int groupSize = this.getGroup(classA.getGroupID()).getGroupSize();

			if (roomCapacity < groupSize) {
				clashes++;
			}

			// Check if room is taken
			for (Class classB : this.classes) {
				if (classA.getRoomID() == classB.getRoomID() && classA.getClassId() != classB.getClassId()
				&& classB.getStartHours() == classA.getStartHours() && classB.getfinishHours() == classA.getfinishHours()) {
					clashes++;
					break;
				}
			}

			// Check if professor is available
			for (Class classB : this.classes) {
				if (classA.getProfessorID() == classB.getProfessorID() && classA.getClassId() != classB.getClassId()
				&& classB.getStartHours() == classA.getStartHours() && classB.getfinishHours() == classA.getfinishHours() ) {
					clashes++;
					break;
				}
			}
		}

		return clashes;
	}
}