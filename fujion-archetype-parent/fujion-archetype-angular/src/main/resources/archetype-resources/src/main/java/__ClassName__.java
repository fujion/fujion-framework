package ${package};

import java.util.Map;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.angular.AngularComponent;
import org.fujion.common.Logger;

@Component(
		tag = "${classname}",
		widgetModule = "fujion-angular-widget",
		widgetClass = "AngularWidget",
		parentTag = "*",
		description="${displayName}")
public class ${ClassName} extends AngularComponent {

	private static final Logger log = Logger.create(${ClassName}.class);

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
