package Components;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;
 
public class NewGroupDialogViewModel extends SelectorComposer<Component> {
    private static final long serialVersionUID = 1L;
    
    @Wire
    Window modalDialog;
     
    @Listen("onClick = #closeBtn")
    public void showModal(Event e) {
        modalDialog.detach();
    }
}