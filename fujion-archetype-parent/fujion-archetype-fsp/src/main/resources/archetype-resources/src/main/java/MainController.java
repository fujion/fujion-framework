package ${package};

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.fujion.ancillary.IAutoWired;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.OnFailure;
import org.fujion.annotation.WiredComponent;
import org.fujion.component.BaseComponent;
import org.fujion.event.Event;

public class MainController implements IAutoWired {

	private static final Log log = LogFactory.getLog(MainController.class);

	public MainController() {
	}
	
	@Override
   public void afterInitialized(BaseComponent root) {
   }

}
