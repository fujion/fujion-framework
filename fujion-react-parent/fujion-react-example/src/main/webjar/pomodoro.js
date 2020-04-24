'use strict';

Object.defineProperty(exports, "__esModule", {
	value: true
});
exports.ReactComponent = undefined;

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var ImageComponent = function (_Component) {
	_inherits(ImageComponent, _Component);

	function ImageComponent() {
		_classCallCheck(this, ImageComponent);

		return _possibleConstructorReturn(this, (ImageComponent.__proto__ || Object.getPrototypeOf(ImageComponent)).apply(this, arguments));
	}

	_createClass(ImageComponent, [{
		key: 'render',
		value: function render() {
			return _react2.default.createElement('img', {
				src: 'webjars/fujion-react-example/assets/img/pomodoro.png',
				alt: 'Pomodoro'
			});
		}
	}]);

	return ImageComponent;
}(_react.Component);

var CounterComponent = function (_Component2) {
	_inherits(CounterComponent, _Component2);

	function CounterComponent() {
		_classCallCheck(this, CounterComponent);

		return _possibleConstructorReturn(this, (CounterComponent.__proto__ || Object.getPrototypeOf(CounterComponent)).apply(this, arguments));
	}

	_createClass(CounterComponent, [{
		key: 'formatTime',
		value: function formatTime() {
			return format(this.props.minutes) + ':' + format(this.props.seconds);

			function format(value) {
				return (value + 100).toString().substring(1);
			}
		}
	}, {
		key: 'render',
		value: function render() {
			return _react2.default.createElement('h1', {}, this.formatTime());
		}
	}]);

	return CounterComponent;
}(_react.Component);

var ButtonComponent = function (_Component3) {
	_inherits(ButtonComponent, _Component3);

	function ButtonComponent() {
		_classCallCheck(this, ButtonComponent);

		return _possibleConstructorReturn(this, (ButtonComponent.__proto__ || Object.getPrototypeOf(ButtonComponent)).apply(this, arguments));
	}

	_createClass(ButtonComponent, [{
		key: 'render',
		value: function render() {
			return _react2.default.createElement('button', {
				className: 'btn btn-danger',
				onClick: this.props.onClick
			}, this.props.buttonLabel);
		}
	}]);

	return ButtonComponent;
}(_react.Component);

// Return the class for the top level component.

var PomodoroComponent = function (_Component4) {
	_inherits(PomodoroComponent, _Component4);

	function PomodoroComponent() {
		_classCallCheck(this, PomodoroComponent);

		var _this4 = _possibleConstructorReturn(this, (PomodoroComponent.__proto__ || Object.getPrototypeOf(PomodoroComponent)).call(this));

		_this4.state = _this4.getBaselineState();
		return _this4;
	}

	_createClass(PomodoroComponent, [{
		key: 'getBaselineState',
		value: function getBaselineState() {
			return {
				isPaused: true,
				minutes: 24,
				seconds: 59,
				buttonLabel: 'Start'
			};
		}
	}, {
		key: 'componentDidMount',
		value: function componentDidMount() {
			var _this5 = this;

			this.timer = setInterval(function () {
				return _this5.tick();
			}, 1000);
		}
	}, {
		key: 'componentWillUnmount',
		value: function componentWillUnmount() {
			clearInterval(this.timer);
			delete this.timer;
		}
	}, {
		key: 'resetPomodoro',
		value: function resetPomodoro() {
			this.setState(this.getBaselineState());
		}
	}, {
		key: 'tick',
		value: function tick() {
			if (!this.state.isPaused) {
				var newState = {};
				newState.buttonLabel = 'Pause';
				newState.seconds = this.state.seconds - 1;

				if (newState.seconds < 0) {
					newState.seconds = 59;
					newState.minutes = this.state.minutes - 1;

					if (newState.minutes < 0) {
						return this.resetPomodoro();
					}
				}

				this.setState(newState);
			}
		}
	}, {
		key: 'togglePause',
		value: function togglePause() {
			var newState = {};
			newState.isPaused = !this.state.isPaused;

			if (this.state.minutes < 24 || this.state.seconds < 59) {
				newState.buttonLabel = newState.isPaused ? 'Resume' : 'Pause';
			}

			this.setState(newState);
		}
	}, {
		key: 'render',
		value: function render() {
			return _react2.default.createElement('div', {
				className: 'text-center'
			}, [_react2.default.createElement(ImageComponent, {
				key: 'image'
			}), _react2.default.createElement(CounterComponent, {
				key: 'counter',
				minutes: this.state.minutes,
				seconds: this.state.seconds
			}), _react2.default.createElement(ButtonComponent, {
				key: 'button',
				buttonLabel: this.state.buttonLabel,
				onClick: this.togglePause.bind(this)
			})]);
		}
	}]);

	return PomodoroComponent;
}(_react.Component);

// Must export component to be instantiated as ReactComponent

exports.ReactComponent = PomodoroComponent;
