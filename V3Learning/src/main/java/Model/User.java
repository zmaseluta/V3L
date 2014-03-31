package Model;

public class User {
	private int id;
	private String lastName;
	private String firstName;
	private String email;
	private String password;
	private String birthDate;
	private int age;
	private float rank;
	private int rankNoUsers;
	private int isValid;
	private int isPublic;
	private String type;
	private Skill[] skills;

	// public User[] friends;
	// public Group[] groups;
	// public Event[] events;
	// public Event[] courses;

	public User() {

	}

	public User(int id, String lastName, String firstName, String email,
			String password, String birthDate, float rank, int isValid,
			int isPublic, String type) {
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.age = computeAge();
		this.rank = rank;
		this.rankNoUsers = Methods.computeRankNoUsers(id);
		this.isValid = isValid;
		this.isPublic = isPublic;
		this.type = type;
		this.skills = Methods.getSkillList(id);
	}

	private int computeAge() {
		return 20;
	}

	public int rank(User selectedUser, int grade) {
		int done;
		done = Methods.rank(this.id, selectedUser.id, grade);

		return done;
	}

	public int commitToDB() {
		int done;
		done = Methods.commitToDB(this);

		return done;
	}

	public int addSkill(Skill s) {
		int done;
		done = Methods.addUserSkill(s.id, this.id);

		return done;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getRank() {
		return rank;
	}

	public void setRank(float rank) {
		this.rank = rank;
	}

	public int getRankNoUsers() {
		return rankNoUsers;
	}

	public void setRankNoUsers(int rankNoUsers) {
		this.rankNoUsers = rankNoUsers;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public int getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Skill[] getSkills() {
		return skills;
	}

	public void setSkills(Skill[] skills) {
		this.skills = skills;
	}
}
