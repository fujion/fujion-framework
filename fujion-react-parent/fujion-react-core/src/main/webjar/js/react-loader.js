'use strict';

if (!fujion.debug) {
  module.exports = require('webjars/react/umd/react.production.min.js');
} else {
  module.exports = require('webjars/react/umd/react.development.js');
}
