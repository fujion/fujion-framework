import React, { Component } from 'react';

class ${ClassName} extends Component {

	componentDidMount() {
	}

	componentWillUnmount() {
	}

	render() {
		return React.createElement('div', {
			className : 'text-center'
		}, '${displayName}');
	}
}

// Must export top-level component as ReactComponent

export { ${ClassName} as ReactComponent };
  
