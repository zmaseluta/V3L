package ViewModel.Groups;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import Model.DownloadFile;
import Model.User;
import Model.Group;

public class HomeVM {
	private User user;
	private Group group;
	@Init
	public void init(@QueryParam("gr") int groupId) {
		user = (User) Sessions.getCurrent().getAttribute("user");	
		user.update();
		group = (Group) Sessions.getCurrent().getAttribute("currentGroup");
		
		if(group == null)
			{Executions.sendRedirect("../home.zul");}
		else group.computeGroupLists();
	}

	
	@Command
	public void upload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx){
		//System.out.println(((Object) file).getStreamData());
		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
		String name = event.getMedia().getName();
		String remoteUrl = Model.UploadFile.upload(event.getMedia().getStreamData(), event.getMedia().getName());
		System.out.println(event.getMedia().getName());
		Model.File f =new Model.File(remoteUrl,name,user.getId(),group.getId(),"");
		user.addFile(f);
		
	}
	
	
	@Command
	public void Logout(){
		Sessions.getCurrent().getAttributes().clear();
		Executions.sendRedirect("~/V3L/index.zul");
	}	
	
	public Group getGroup(){
		return group;
	}
	
	@Command
	public void goToUser(@BindingParam("visitUser")User user){
		Executions.sendRedirect("../otherprofile.zul?us="
				+ user.getId());
	}
	
	@Command
	@NotifyChange("user")
	public void leaveGroup(){
		group = (Group) Sessions.getCurrent().getAttribute("currentGroup");
		user.removeGroup(group);
		Executions.sendRedirect("home.zul?gr="
				+ group.getId());
	}
	
	@Command
	@NotifyChange("user")
	public void joinGroup(){
		group = (Group) Sessions.getCurrent().getAttribute("currentGroup");
		user.addGroup(group);
		Executions.sendRedirect("home.zul?gr="
				+ group.getId());
	}
	
	@Command
	@NotifyChange("user")
	public void addFriend(@BindingParam("visitUser")User usr){
		user.addFriend(usr);
		Executions.sendRedirect("home.zul?gr="
				+ group.getId());
	}
	
	@Command
	@NotifyChange("user")
	public void removeFriend(@BindingParam("visitUser")User usr){
		if (user.removeFriend(usr) == 1)
		{
		user.computeUserLists();
		Executions.sendRedirect("home.zul?gr=" + group.getId());
		}
	}
	
	@Command
	public void filedownload(){
		System.out.println("download");
		Object o = new DownloadFile();
	}
}
