System.import('fujion-core').then(function(fujion) {
	var msg = 'External client script was executed.';
	fujion.widget._page.trigger('log', {data: msg});
	console.log(msg);
});