package Model;

public class Domain {
	
	public int id = 0;
	public String name = null;

	public Domain(String name) {
		this.name = name;
	}
	
	public int commitToDB () {
		int done;
		done = Methods.commitToDB(this);
		
		return done;
	}

}
