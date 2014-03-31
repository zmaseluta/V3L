package Model;

public class Skill {
	
	public int id;
	public String name;
	public int domainId;

	public Skill(String name) {
		this.name = name;
	}
	
	public int addToDomain (Domain d) {
		int done;
		this.domainId = d.id;
		done = Methods.commitToDB(this);
		
		return done;
	}

}
