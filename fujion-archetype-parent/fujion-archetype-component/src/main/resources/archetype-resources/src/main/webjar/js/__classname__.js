'use strict';

define('${artifactId}', [
	'fujion-core', 
	'fujion-widget',
	'${artifactId}-css'
	], 
	
	function(fujion, Widget) { 
	
	var ${ClassName} = Widget.UIWidget.extend({
	
		/*------------------------------ Containment ------------------------------*/

		
		/*------------------------------ Lifecycle ------------------------------*/

		init: function() {
			this._super();
		},
		
		/*------------------------------ Other ------------------------------*/
	
		
		/*------------------------------ Rendering ------------------------------*/
		
		render$: function() {
			return $('<label>${displayName}</label>');
		}
		
		/*------------------------------ State ------------------------------*/
		
	});

	return Widget.addon('${package}', '${ClassName}', ${ClassName});
});