'use strict';

define('${artifactId}', [
	'fujion-core', 
	'fujion-widget',
	'${artifactId}-css'
	], 
	
	(fujion, Widget) => {
	
	var ${ClassName} = Widget.UIWidget.extend({
	
		/*------------------------------ Containment ------------------------------*/

		
		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
		},
		
		/*------------------------------ Other ------------------------------*/
	
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			return $('<div><label>${displayName}</label></div>');
		}
		
		/*------------------------------ State ------------------------------*/
		
	});

	return Widget.addon('${package}', '${ClassName}', ${ClassName});
});
