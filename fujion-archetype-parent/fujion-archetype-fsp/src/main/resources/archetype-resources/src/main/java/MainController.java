package ${package};

import org.fujion.ancillary.IAutoWired;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.OnFailure;
import org.fujion.annotation.WiredComponent;
import org.fujion.component.BaseComponent;
import org.fujion.common.Logger;
import org.fujion.event.Event;

public class MainController implements IAutoWired {

	private static final Logger log = Logger.create(MainController.class);

	public MainController() {
	}
	
	@Override
   public void afterInitialized(BaseComponent root) {
   }

}
