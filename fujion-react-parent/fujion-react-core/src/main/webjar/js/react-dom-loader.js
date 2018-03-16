'use strict';

if (!fujion.debug) {
  module.exports = require('webjars/react-dom/umd/react-dom.production.min.js');
} else {
  module.exports = require('webjars/react-dom/umd/react-dom.development.js');
}
