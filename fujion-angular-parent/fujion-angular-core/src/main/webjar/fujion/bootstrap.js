"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var platform_browser_dynamic_1 = require("@angular/platform-browser-dynamic");
var core_2 = require("@angular/core");
function AppContext(aModule, selector) {
    var appContext = this;
    var ngModule;
    var App = aModule.AngularComponent;
    if (App) {
        ngModule = {
            imports: [platform_browser_1.BrowserModule],
            declarations: [App],
            entryComponents: [App]
        };
        aModule.ngModule ? Object.assign(ngModule, aModule.ngModule) : null;
    }
    else if (aModule.ngModule) {
        ngModule = aModule.ngModule;
    }
    else {
        aModule = aModule.AngularModule || aModule;
        ngModule = findDecorator(aModule);
        if (!ngModule) {
            throw 'No NgModule decorator for Angular module';
        }
    }
    App = App || (ngModule.bootstrap ? ngModule.bootstrap[0] : null);
    if (!App) {
        throw 'No Angular bootstrap target specified';
    }
    delete ngModule.bootstrap;
    function findDecorator(obj) {
        var metadata = Reflect.getMetadata('annotations', obj);
        if (metadata) {
            for (var _i = 0, metadata_1 = metadata; _i < metadata_1.length; _i++) {
                var md = metadata_1[_i];
                if (md.toString() === '@NgModule') {
                    return md;
                }
            }
        }
        return null;
    }
    var AppModule = (function () {
        function AppModule(resolver, ngZone) {
            this.resolver = resolver;
            this.ngZone = ngZone;
            appContext.zone = ngZone;
        }
        AppModule.prototype.ngDoBootstrap = function (appRef) {
            var factory = this.resolver.resolveComponentFactory(App);
            appContext.componentRef = appRef.bootstrap(factory, selector);
        };
        return AppModule;
    }());
    AppModule = __decorate([
        core_1.NgModule(ngModule),
        __metadata("design:paramtypes", [core_2.ComponentFactoryResolver,
            core_2.NgZone])
    ], AppModule);
    AppContext.prototype.isLoaded = function () {
        return !!this.moduleRef;
    };
    AppContext.prototype.bootstrap = function (compilerOptions) {
        var _this = this;
        var platform = platform_browser_dynamic_1.platformBrowserDynamic();
        return platform.bootstrapModule(AppModule, compilerOptions).then(function (ref) { return _this.moduleRef = ref; });
    };
    AppContext.prototype.destroy = function () {
        this.moduleRef ? this.moduleRef.destroy() : null;
        this.moduleRef = null;
    };
    AppContext.prototype.invoke = function (functionName, args) {
        var _this = this;
        return this.zone.run(function () {
            var instance = _this.componentRef.instance;
            instance[functionName].apply(instance, args);
        });
    };
}
exports.AppContext = AppContext;
//# sourceMappingURL=bootstrap.js.map