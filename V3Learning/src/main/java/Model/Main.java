package Model;

import java.util.Calendar;
import java.util.List;

public class Main {

	/*
	 * TEST MAIN
	 * 
	 * 
	 * 
	 * public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		String user = "echipab6";
		String pass = "echipab6";
		String dbDriver = "jdbc:mysql://db4free.net:3306/virtual3l";
		
		//String user = "root";
		//String pass = "";
		//String dbDriver = "jdbc:mysql://localhost/v3l";
		String dbClass = "com.mysql.jdbc.Driver";
		
		DBConnection dbc = new DBConnection();
		dbc.connectToDB(user, pass, dbDriver, dbClass);
		System.out.println(dbc.getIsConnected());
		
		DBOperations dbo = new DBOperations(dbc);
		
		
		int val;
		
		val = dbo.registerUser("abc", "abc", "abc", "abc");
		System.out.println(val);
		
		
		User u = dbo.login("aaa@bbb.com", "aaaa");
		if(u != null){
			System.out.println(u.getLastName());
		}
		
		Skill s = dbo.getSkill("Camp seting");
		if(s != null){
			System.out.println(s.getName());
		}
		
		List<Skill> sList = dbo.getAllSkills();
		for(int i=0; i<sList.size(); i++){
			System.out.println(sList.get(i).getName());
		}
		
		List<User> uList = dbo.getAllUsers();
		for(int i=0; i<uList.size(); i++){
			System.out.println(uList.get(i).getEmail());
		}
		
		List<Domain> dList = dbo.getAllDomains();
		for(int i=0; i<dList.size(); i++){
			System.out.println(dList.get(i).getName());
		}
		
		Domain d = new Domain(dbc, "Very useful");
		val = d.add();
		System.out.println(val);
		d = dbo.getDomain("Very useful");
		Skill sk = new Skill(dbc, "Water boiling");
		if(d != null) {
			val = sk.addToDomain(d);
			System.out.println(val);
		}
		
		u = dbo.login("gi@mail.com", "iulia");
		System.out.println(u.getAge());
		//List<User> bla = u.getFriendList();
		//for(int i=0; i<bla.size(); i++){
			//System.out.println(bla.get(i).getEmail());
		//}
		
		
		sk = dbo.getSkill("Water boiling");
		val = u.addSkill(sk);
		System.out.println(val);
		
		//val = u.removeSkill(sk);
		//System.out.println(val);
		
		for(int i=0; i<u.getSkills().size(); i++){
			System.out.println(u.getSkills().get(i).getName());
		}
		
		u.setIsPublic(0);
		val = u.update();
		System.out.println(val);
		
		System.out.println(u.getRankNoUsers());
		
		//User u2 = dbo.login("vm@mail.com", "mona");
		//val = u2.rank(u, 5);
		//System.out.println(val);
		
		//val = u2.removeFriend(u);
		//System.out.println(val);
		
		//val = u2.addFriend(u);
		//System.out.println(val);
		
		
		
		
		System.out.println(dbc.closeConnection());
		System.out.println(dbc.getIsConnected());
	}*/
}
