package ${package};

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseUIComponent;

@Component(tag = "${classname}", widgetModule = "${artifactId}", widgetClass = "${ClassName}", parentTag = "*")
public class ${ClassName} extends BaseUIComponent {

	private static final Log log = LogFactory.getLog(${ClassName}.class);

	public ${ClassName}() {
	}
	
    @Override
    protected void initProps(Map<String, Object> props) {
        super.initProps(props);
        props.put("wclazz", "${classname}");
    }

}
