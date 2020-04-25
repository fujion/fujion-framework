var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
define(["require", "exports", "./pomodoro.component", "@angular/core", "@angular/platform-browser"], function (require, exports, pomodoro_component_1, core_1, platform_browser_1) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var AngularModule = /** @class */ (function () {
        function AngularModule() {
        }
        AngularModule = __decorate([
            core_1.NgModule({
                imports: [platform_browser_1.BrowserModule],
                bootstrap: [pomodoro_component_1.PomodoroComponent],
                declarations: [pomodoro_component_1.PomodoroComponent],
                entryComponents: [pomodoro_component_1.PomodoroComponent]
            })
        ], AngularModule);
        return AngularModule;
    }());
    exports.AngularModule = AngularModule;
});
//# sourceMappingURL=pomodoro.module.js.map