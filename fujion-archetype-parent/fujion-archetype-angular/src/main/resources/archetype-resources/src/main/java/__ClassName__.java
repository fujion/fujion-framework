package ${package};

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.angular.AngularComponent;

@Component(tag = "${classname}", widgetModule = "fujion-angular-widget", widgetClass = "AngularWidget", parentTag = "*")
public class ${ClassName} extends AngularComponent {

	private static final Log log = LogFactory.getLog(${ClassName}.class);

	public ${ClassName}() {
		super.setSrc("${artifactId}");
	}
	
	@Override
   protected void _initProps(Map<String, Object> props) {
   	super._initProps(props);
   	props.put("wclazz", "${classname}");
   }

   @PropertySetter(value = "src", hide = true)
   public void setSrc(String src) {
      throw new UnsupportedOperationException();
   }

}
