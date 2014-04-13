package Components;
 
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Window;
 
public class ModalDialogViewModel {     
    
    @Command
    public void showModal() {
        Window window = (Window)Executions.createComponents(
                "/components/new_group.zul", null, null);
        window.doModal();
    }
}