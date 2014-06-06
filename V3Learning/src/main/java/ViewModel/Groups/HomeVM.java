package ViewModel.Groups;

import java.io.File;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;

import Model.DBConnection;
import Model.DBOperations;
import Model.User;
import Model.Group;

public class HomeVM {
	private User user;
	private Group group;
	
	@Init
	public void init(@QueryParam("gr") int groupId) {
		user = (User) Sessions.getCurrent().getAttribute("user");	
		group = (Group) Sessions.getCurrent().getAttribute("currentGroup");
		
		if(group ==null)
			{Executions.sendRedirect("~/V3L/index.zul");}
	}

	
	@Command
	public void upload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx){
		//System.out.println(((Object) file).getStreamData());
		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
		String name = event.getMedia().getName();
		String remoteUrl = Model.UploadFile.upload(event.getMedia().getStreamData(), event.getMedia().getName());
		System.out.println(event.getMedia().getName());
		Model.File f =new Model.File(remoteUrl,name,user.getId(),1,"");
		user.addFile(f);
	}
	
	@Command
	public void goToUser(@BindingParam("visitUser")User user){
		Executions.sendRedirect("otherprofile.zul?us="
				+ user.getId());
	}
	
	@Command
	public void Logout(){
		Sessions.getCurrent().getAttributes().clear();
		Executions.sendRedirect("~/V3L/index.zul");
	}	
	
	public Group getGroup(){
		return group;
	}
}
