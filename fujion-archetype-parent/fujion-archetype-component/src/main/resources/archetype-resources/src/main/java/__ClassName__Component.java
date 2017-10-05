package ${package};

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseComponent;

@Component(tag = "${classname}", widgetModule = "${artifactId}", widgetClass = "${ClassName}", parentTag = "*")
public class ${ClassName}Component extends BaseComponent {

	private static final Log log = LogFactory.getLog(${ClassName}Component.class);

	public ${ClassName}Component() {
	}
	
    @Override
    protected void _initProps(Map<String, Object> props) {
        props.put("wclazz", "${artifactId}");
        super._initProps(props);
    }

}
