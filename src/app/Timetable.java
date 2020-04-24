package app;

import java.time.LocalTime;
import java.util.HashMap;

public class Timetable {
	private final HashMap<Integer, Room> rooms;
	private final HashMap<Integer, Professor> professors;
	private final HashMap<Integer, Module> modules;
	private final HashMap<Integer, Group> groups;
	private final HashMap<Integer, Timeslot> timeslots;
	private final HashMap<Integer, Unvailable> groupUnvailable;
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
		this.groupUnvailable = new HashMap<Integer, Unvailable>();
	}

	public Timetable(Timetable cloneable) {
		this.rooms = cloneable.getRooms();
		this.professors = cloneable.getProfessors();
		this.modules = cloneable.getModules();
		this.groups = cloneable.getGroups();
		this.timeslots = cloneable.getTimeslots();
		this.groupUnvailable = cloneable.getGroupUnvailable();
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

	private HashMap<Integer, Unvailable> getGroupUnvailable() {
		return this.groupUnvailable;
	}

	public Unvailable[] getGroupUnvailables() {
		return (Unvailable[]) this.groupUnvailable.values().toArray(new Unvailable[this.groupUnvailable.size()]);
	}

	public void addUnvailableGroup(int id, int day, int startHours, int finishHours, int groupID) {
		this.groupUnvailable.put(id, new Unvailable(day, startHours, finishHours, groupID));
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

	public Unvailable getUnvailablebyGroup(int id) {
		return this.groupUnvailable.get(id);
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

	public int calcClashes() {
		int clashes = 0;

		for (Class classA : this.classes) {
			LocalTime startA = LocalTime.of(classA.getStartHours(), 0);
			LocalTime finishA = LocalTime.of(classA.getfinishHours(), 0);

			// Check room capacity
			int roomCapacity = this.getRoom(classA.getRoomID()).getcapacity();
			int groupSize = this.getGroup(classA.getGroupID()).getGroupSize();

			if (roomCapacity < groupSize) {
				clashes++;
			}

			// Check if Group is available
			for (Unvailable unvailable : this.getGroupUnvailables()) {
				LocalTime startB = LocalTime.of(unvailable.getStartHours(), 0);
				LocalTime finishB = LocalTime.of(unvailable.getfinishHours(), 0);
				Boolean overlap = ((startA.isBefore(finishB)) && (finishA.isAfter(startB)));
				if (classA.getDay() == unvailable.getDay() 
						&& overlap) {
					clashes++;
					break;
				}
			}
			// Check if room is taken
			for (Class classB : this.classes) {
				LocalTime OnestartB = LocalTime.of(classB.getStartHours(), 0);
				LocalTime OnefinishB = LocalTime.of(classB.getfinishHours(), 0);

				Boolean oneoverlap = ((startA.isBefore(OnefinishB)) && (finishA.isAfter(OnestartB)));
				if (classA.getRoomID() == classB.getRoomID() && classA.getClassId() != classB.getClassId() && oneoverlap
						&& classA.getDay() == classB.getDay()) {
					clashes++;
					break;
				}
			}

			// Check if Group is available
			for (Class classB : this.classes) {
				LocalTime twostartB = LocalTime.of(classB.getStartHours(), 0);
				LocalTime twofinishB = LocalTime.of(classB.getfinishHours(), 0);

				Boolean twooverlap = ((startA.isBefore(twofinishB)) && (finishA.isAfter(twostartB)));
				if (classA.getProfessorID() == classB.getProfessorID() && classA.getClassId() != classB.getClassId()
						&& twooverlap && classA.getDay() == classB.getDay()) {
					clashes++;
					break;
				}
			}
		}

		return clashes;
	}
}