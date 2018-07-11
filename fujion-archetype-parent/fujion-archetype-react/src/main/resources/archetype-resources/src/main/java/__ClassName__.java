package ${package};

import java.util.Map;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.common.Logger;
import org.fujion.react.ReactComponent;

@Component(tag = "${classname}", widgetModule = "fujion-react-widget", widgetClass = "ReactWidget", parentTag = "*", description="${displayName}")
public class ${ClassName} extends ReactComponent {

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
