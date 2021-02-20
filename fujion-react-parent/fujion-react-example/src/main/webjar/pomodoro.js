System.register(["react"], function (_export, _context) {
  "use strict";

  var React, Component, ImageComponent, CounterComponent, ButtonComponent, PomodoroComponent;
  return {
    setters: [function (_react) {
      React = _react.default;
      Component = _react.Component;
    }],
    execute: function () {
      ImageComponent = class ImageComponent extends Component {
        render() {
          return /*#__PURE__*/React.createElement('img', {
            src: 'webjars/fujion-react-example/assets/img/pomodoro.png',
            alt: 'Pomodoro'
          });
        }

      };
      CounterComponent = class CounterComponent extends Component {
        formatTime() {
          return format(this.props.minutes) + ':' + format(this.props.seconds);

          function format(value) {
            return (value + 100).toString().substring(1);
          }
        }

        render() {
          return /*#__PURE__*/React.createElement('h1', {}, this.formatTime());
        }

      };
      ButtonComponent = class ButtonComponent extends Component {
        render() {
          return /*#__PURE__*/React.createElement('button', {
            className: 'btn btn-danger',
            onClick: this.props.onClick
          }, this.props.buttonLabel);
        }

      }; // Return the class for the top level component.

      _export("ReactComponent", PomodoroComponent = class PomodoroComponent extends Component {
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
          return /*#__PURE__*/React.createElement('div', {
            className: 'text-center'
          }, [/*#__PURE__*/React.createElement(ImageComponent, {
            key: 'image'
          }), /*#__PURE__*/React.createElement(CounterComponent, {
            key: 'counter',
            minutes: this.state.minutes,
            seconds: this.state.seconds
          }), /*#__PURE__*/React.createElement(ButtonComponent, {
            key: 'button',
            buttonLabel: this.state.buttonLabel,
            onClick: this.togglePause.bind(this)
          })]);
        }

      }); // Must export component to be instantiated as ReactComponent

    }
  };
});

//# sourceMappingURL=pomodoro.js.map