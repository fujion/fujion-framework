/*
 *  Copyright 2017 Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var pomodoro_component_1 = require("./pomodoro.component");
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var AngularModule = (function () {
    function AngularModule() {
    }
    return AngularModule;
}());
AngularModule = __decorate([
    core_1.NgModule({
        imports: [platform_browser_1.BrowserModule],
        bootstrap: [pomodoro_component_1.PomodoroComponent],
        declarations: [pomodoro_component_1.PomodoroComponent],
        entryComponents: [pomodoro_component_1.PomodoroComponent]
    })
], AngularModule);
exports.AngularModule = AngularModule;
//# sourceMappingURL=pomodoro.module.js.map