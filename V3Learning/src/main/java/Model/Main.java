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
		
	}

	public static void main(String[] args) {

		/*
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
		
		/*
		int val;
		
		Domain d = dbo.getDomain("Cooking");
		System.out.println(d.getId());
		List<Group> list = dbo.getAllGroupsInDomain(d);
		if (list != null) {
			for(int i = 0; i<list.size(); i++) {
				System.out.println(list.get(i).getName());
			}
		}
		Group gr = list.get(0);
		List<Event> list2 = dbo.getAllEventsInGroup(list.get(0));
		if (list2 != null) {
			for(int i = 0; i<list2.size(); i++) {
				System.out.println(list2.get(i).getName() + " " + list2.get(i).getIsExpired());
			}
		}
		
		List<File> list3 = dbo.getAllFilesInGroup(list.get(0));
		if (list3 != null) {
			for(int i = 0; i<list3.size(); i++) {
				System.out.println(list3.get(i).getName());
			}
		}
		
		System.out.println(list.get(0).getId());
		list.get(0).computeGroupLists();
		List<User> memb = list.get(0).getMembers();
		List<Event> ev = list.get(0).getEvents();
		List<File> fi = list.get(0).getFiles();
		if (memb != null) {
			for(int i = 0; i < memb.size(); i++) {
				System.out.println(memb.get(i).getFirstName());
			}
		}
		
		if (ev != null) {
			for(int i = 0; i < ev.size(); i++) {
				System.out.println(ev.get(i).getName());
			}
		}
		
		if (fi != null) {
			for(int i = 0; i < fi.size(); i++) {
				System.out.println(fi.get(i).getName());
			}
		}
		
		list2.get(1).computeEventLists();
		List<User> memb2 = list2.get(1).getMembers();
		if (memb2 != null) {
			for(int i = 0; i < memb2.size(); i++) {
				System.out.println(memb2.get(i).getFirstName());
			}
		}
		
		User us = dbo.login("vm@mail.com", "mona");
		if (us != null) {
			val = us.createGroup("Razgaiatilor", dbo.getDomain("Very useful").getId(), 
					"Aici ti se cuvine!");
			System.out.println(val);
			list = dbo.getAllGroupsInDomain(dbo.getDomain("Very useful"));
			val = us.createEvent("hai sa ne laudam", list.get(0).getId(), "2014-10-10", "...");
			System.out.println(val);
			val = us.addGroup(list.get(0));
			System.out.println(val);
			list2 = dbo.getAllEventsInGroup(list.get(0));
			val = us.addEvent(list2.get(0));
			System.out.println(val);
			//list2.get(0).setName("Hai sa ne laudam!");
			//val = us.updateEvent(list2.get(0));
			//System.out.println(val);
			//list.get(0).setDescription("Aici ti se cuvine! :D");
			//val = us.updateGroup(list.get(0));
			//System.out.println(val);
			us.computeUserLists();
			for(int i = 0; i < us.getGroups().size(); i++) {
				System.out.println(us.getGroups().get(i).getName());
			}
			for(int i = 0; i < us.getEvents().size(); i++) {
				System.out.println(us.getEvents().get(i).getName());
			}
			for(int i = 0; i < us.getCourses().size(); i++) {
				System.out.println(us.getCourses().get(i).getName());
			}
			
			File f = new File("undeva pe net", "Top Secret", us.getId(), 
					list.get(0).getId(), "adresa mea");
			val = us.addFile(f);
			System.out.println(val);
			//list.get(0).computeGroupLists();
			//f = list.get(0).getFiles().get(0);
			//val = us.removeFile(f);
			//System.out.println(val);
		}
		*/
		/*
		List<User> searchU = dbo.searchUsers("vi");
		for(int i=0; i < searchU.size(); i++) {
			System.out.println(searchU.get(i).getId());
		}
		
		List<Domain> searchD = dbo.searchDomain("of it");
		for(int i=0; i < searchD.size(); i++) {
			System.out.println(searchD.get(i).getId());
		}
		
		List<Skill> searchS = dbo.searchSkill("con se");
		for(int i=0; i < searchS.size(); i++) {
			System.out.println(searchS.get(i).getId());
		}
		
		List<Group> searchG = dbo.searchGroup("vi ra lo");
		for(int i=0; i < searchG.size(); i++) {
			System.out.println(searchG.get(i).getId());
		}
		
		List<Event> searchE = dbo.searchEvent("! hai");
		for(int i=0; i < searchE.size(); i++) {
			System.out.println(searchE.get(i).getId());
		}

		List<File> searchF = dbo.searchFile("m :d");
		for(int i=0; i < searchF.size(); i++) {
			System.out.println(searchF.get(i).getId());
		}
		
		List<File> searchV = dbo.searchVideo("m top");
		for(int i=0; i < searchV.size(); i++) {
			System.out.println(searchV.get(i).getId());
		}
		
		User us = dbo.login("vm@mail.com", "mona");
		File f = new File("altundeva pe net", "Nimic special", us.getId(), 
				5, "");
		int val = us.addVideo(f);
		System.out.println(val);
		
		Skill s = dbo.getSkill("Salads");
		List<User> users = dbo.getAllUsersWithSkill(s);
		for(int i=0; i < users.size(); i++) {
			System.out.println(users.get(i).getId());
		}
		
		Domain d = dbo.getDomain("Cooking");
		List<User> users2 = dbo.getAllUsersInGroup(dbo.getAllGroupsInDomain(d).get(0));
		for(int i=0; i < users2.size(); i++) {
			System.out.println(users2.get(i).getId());
		}
		Skill skill = dbo.getSkill("Nonverbal");
		System.out.println(skill.getDomainName());
		*/
		
		//User us = dbo.login("vm@mail.com", "mona");
		
		/*
		int val = us.createGroup("NewGroup2", 1, "test");
		System.out.println(val);
		us.computeUserLists();
		List<Group> gr = us.getSuggestedGroupsList();
		if(gr != null){
			for(int i=0; i<gr.size(); i++){
				System.out.println(gr.get(i).getName());
			}
		}
		List<User> fr = us.getSuggestedFriendsList();
		if(fr != null){
			for(int i=0; i<fr.size(); i++){
				System.out.println(fr.get(i).getFirstName());
			}
		}
		
		
		//List<Event> evL = dbo.searchEvent("ciorba");
		//Event e = evL.get(0);
		//int val = us.postInEvent(e, "Nu-mi place ciorba!");
		//System.out.println(val);
		//e.computeEventLists();
		//Post p = e.getPosts().get(0);
		//System.out.println(p.getContent());
		//int val = us.commentPost(p, "Ba nu!");
		//System.out.println(val);
		//p.computePostLists();
		//Comment c = p.getComments().get(0);
		//System.out.println(c.getContent());
		//c = p.getComments().get(2);
		//System.out.println(c.getContent());
		//int val = us.deletePostComment(c);
		//System.out.println(val);
		//val = us.deletePostFromEvent(p);
		//System.out.println(val);
		//int val = us.postInEvent(e, "Ciorba e buna!");
		//System.out.println(val);
		//p = e.getPosts().get(2);
		//System.out.println(p.getContent());
		//int val = us.deletePostFromEvent(p);
		//System.out.println(val);
		
		//us.computeUserLists();
		//System.out.println(us.getPicURL());
		//us.setPicURL("bbbbbbbbbbbb");
		//us.update();
		//System.out.println(us.getPicURL());
		
		System.out.println(dbc.closeConnection());
		System.out.println(dbc.getIsConnected());
		
		
	}*/
}
