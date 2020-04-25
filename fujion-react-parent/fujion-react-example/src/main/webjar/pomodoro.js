define(["exports", "react"], function (_exports, _react) {
  "use strict";

  Object.defineProperty(_exports, "__esModule", {
    value: true
  });
  _exports.ReactComponent = void 0;
  _react = _interopRequireWildcard(_react);

  function _getRequireWildcardCache() { if (typeof WeakMap !== "function") return null; var cache = new WeakMap(); _getRequireWildcardCache = function () { return cache; }; return cache; }

  function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } if (obj === null || typeof obj !== "object" && typeof obj !== "function") { return { default: obj }; } var cache = _getRequireWildcardCache(); if (cache && cache.has(obj)) { return cache.get(obj); } var newObj = {}; var hasPropertyDescriptor = Object.defineProperty && Object.getOwnPropertyDescriptor; for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) { var desc = hasPropertyDescriptor ? Object.getOwnPropertyDescriptor(obj, key) : null; if (desc && (desc.get || desc.set)) { Object.defineProperty(newObj, key, desc); } else { newObj[key] = obj[key]; } } } newObj.default = obj; if (cache) { cache.set(obj, newObj); } return newObj; }

  class ImageComponent extends _react.Component {
    render() {
      return _react.default.createElement('img', {
        src: 'webjars/fujion-react-example/assets/img/pomodoro.png',
        alt: 'Pomodoro'
      });
    }

  }

  class CounterComponent extends _react.Component {
    formatTime() {
      return format(this.props.minutes) + ':' + format(this.props.seconds);

      function format(value) {
        return (value + 100).toString().substring(1);
      }
    }

    render() {
      return _react.default.createElement('h1', {}, this.formatTime());
    }

  }

  class ButtonComponent extends _react.Component {
    render() {
      return _react.default.createElement('button', {
        className: 'btn btn-danger',
        onClick: this.props.onClick
      }, this.props.buttonLabel);
    }

  } // Return the class for the top level component.


  class PomodoroComponent extends _react.Component {
    constructor() {
      super();
      this.state = this.getBaselineState();
    }

    getBaselineState() {
      return {
        isPaused: true,
        minutes: 24,
        seconds: 59,
        buttonLabel: 'Start'
      };
    }

    componentDidMount() {
      this.timer = setInterval(() => this.tick(), 1000);
    }

    componentWillUnmount() {
      clearInterval(this.timer);
      delete this.timer;
    }

    resetPomodoro() {
      this.setState(this.getBaselineState());
    }

    tick() {
      if (!this.state.isPaused) {
        const newState = {};
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

    togglePause() {
      const newState = {};
      newState.isPaused = !this.state.isPaused;

      if (this.state.minutes < 24 || this.state.seconds < 59) {
        newState.buttonLabel = newState.isPaused ? 'Resume' : 'Pause';
      }

      this.setState(newState);
    }

    render() {
      return _react.default.createElement('div', {
        className: 'text-center'
      }, [_react.default.createElement(ImageComponent, {
        key: 'image'
      }), _react.default.createElement(CounterComponent, {
        key: 'counter',
        minutes: this.state.minutes,
        seconds: this.state.seconds
      }), _react.default.createElement(ButtonComponent, {
        key: 'button',
        buttonLabel: this.state.buttonLabel,
        onClick: this.togglePause.bind(this)
      })]);
    }

  } // Must export component to be instantiated as ReactComponent


  _exports.ReactComponent = PomodoroComponent;
});
