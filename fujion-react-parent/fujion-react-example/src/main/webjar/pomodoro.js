"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.ReactComponent = void 0;

var _react = _interopRequireWildcard(require("react"));

function _getRequireWildcardCache() { if (typeof WeakMap !== "function") return null; var cache = new WeakMap(); _getRequireWildcardCache = function _getRequireWildcardCache() { return cache; }; return cache; }

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } if (obj === null || _typeof(obj) !== "object" && typeof obj !== "function") { return { "default": obj }; } var cache = _getRequireWildcardCache(); if (cache && cache.has(obj)) { return cache.get(obj); } var newObj = {}; var hasPropertyDescriptor = Object.defineProperty && Object.getOwnPropertyDescriptor; for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) { var desc = hasPropertyDescriptor ? Object.getOwnPropertyDescriptor(obj, key) : null; if (desc && (desc.get || desc.set)) { Object.defineProperty(newObj, key, desc); } else { newObj[key] = obj[key]; } } } newObj["default"] = obj; if (cache) { cache.set(obj, newObj); } return newObj; }

function _typeof(obj) { "@babel/helpers - typeof"; if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; } return _typeof(obj); }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); if (superClass) _setPrototypeOf(subClass, superClass); }

function _setPrototypeOf(o, p) { _setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return _setPrototypeOf(o, p); }

function _createSuper(Derived) { var hasNativeReflectConstruct = _isNativeReflectConstruct(); return function _createSuperInternal() { var Super = _getPrototypeOf(Derived), result; if (hasNativeReflectConstruct) { var NewTarget = _getPrototypeOf(this).constructor; result = Reflect.construct(Super, arguments, NewTarget); } else { result = Super.apply(this, arguments); } return _possibleConstructorReturn(this, result); }; }

function _possibleConstructorReturn(self, call) { if (call && (_typeof(call) === "object" || typeof call === "function")) { return call; } return _assertThisInitialized(self); }

function _assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return self; }

function _isNativeReflectConstruct() { if (typeof Reflect === "undefined" || !Reflect.construct) return false; if (Reflect.construct.sham) return false; if (typeof Proxy === "function") return true; try { Date.prototype.toString.call(Reflect.construct(Date, [], function () {})); return true; } catch (e) { return false; } }

function _getPrototypeOf(o) { _getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return _getPrototypeOf(o); }

var ImageComponent = /*#__PURE__*/function (_Component) {
  _inherits(ImageComponent, _Component);

  var _super = _createSuper(ImageComponent);

  function ImageComponent() {
    _classCallCheck(this, ImageComponent);

    return _super.apply(this, arguments);
  }

  _createClass(ImageComponent, [{
    key: "render",
    value: function render() {
      return _react["default"].createElement('img', {
        src: 'webjars/fujion-react-example/assets/img/pomodoro.png',
        alt: 'Pomodoro'
      });
    }
  }]);

  return ImageComponent;
}(_react.Component);

var CounterComponent = /*#__PURE__*/function (_Component2) {
  _inherits(CounterComponent, _Component2);

  var _super2 = _createSuper(CounterComponent);

  function CounterComponent() {
    _classCallCheck(this, CounterComponent);

    return _super2.apply(this, arguments);
  }

  _createClass(CounterComponent, [{
    key: "formatTime",
    value: function formatTime() {
      return format(this.props.minutes) + ':' + format(this.props.seconds);

      function format(value) {
        return (value + 100).toString().substring(1);
      }
    }
  }, {
    key: "render",
    value: function render() {
      return _react["default"].createElement('h1', {}, this.formatTime());
    }
  }]);

  return CounterComponent;
}(_react.Component);

var ButtonComponent = /*#__PURE__*/function (_Component3) {
  _inherits(ButtonComponent, _Component3);

  var _super3 = _createSuper(ButtonComponent);

  function ButtonComponent() {
    _classCallCheck(this, ButtonComponent);

    return _super3.apply(this, arguments);
  }

  _createClass(ButtonComponent, [{
    key: "render",
    value: function render() {
      return _react["default"].createElement('button', {
        className: 'btn btn-danger',
        onClick: this.props.onClick
      }, this.props.buttonLabel);
    }
  }]);

  return ButtonComponent;
}(_react.Component); // Return the class for the top level component.


var PomodoroComponent = /*#__PURE__*/function (_Component4) {
  _inherits(PomodoroComponent, _Component4);

  var _super4 = _createSuper(PomodoroComponent);

  function PomodoroComponent() {
    var _this;

    _classCallCheck(this, PomodoroComponent);

    _this = _super4.call(this);
    _this.state = _this.getBaselineState();
    return _this;
  }

  _createClass(PomodoroComponent, [{
    key: "getBaselineState",
    value: function getBaselineState() {
      return {
        isPaused: true,
        minutes: 24,
        seconds: 59,
        buttonLabel: 'Start'
      };
    }
  }, {
    key: "componentDidMount",
    value: function componentDidMount() {
      var _this2 = this;

      this.timer = setInterval(function () {
        return _this2.tick();
      }, 1000);
    }
  }, {
    key: "componentWillUnmount",
    value: function componentWillUnmount() {
      clearInterval(this.timer);
      delete this.timer;
    }
  }, {
    key: "resetPomodoro",
    value: function resetPomodoro() {
      this.setState(this.getBaselineState());
    }
  }, {
    key: "tick",
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
    key: "togglePause",
    value: function togglePause() {
      var newState = {};
      newState.isPaused = !this.state.isPaused;

      if (this.state.minutes < 24 || this.state.seconds < 59) {
        newState.buttonLabel = newState.isPaused ? 'Resume' : 'Pause';
      }

      this.setState(newState);
    }
  }, {
    key: "render",
    value: function render() {
      return _react["default"].createElement('div', {
        className: 'text-center'
      }, [_react["default"].createElement(ImageComponent, {
        key: 'image'
      }), _react["default"].createElement(CounterComponent, {
        key: 'counter',
        minutes: this.state.minutes,
        seconds: this.state.seconds
      }), _react["default"].createElement(ButtonComponent, {
        key: 'button',
        buttonLabel: this.state.buttonLabel,
        onClick: this.togglePause.bind(this)
      })]);
    }
  }]);

  return PomodoroComponent;
}(_react.Component); // Must export component to be instantiated as ReactComponent


exports.ReactComponent = PomodoroComponent;
