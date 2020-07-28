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
exports.AppContext = void 0;
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var platform_browser_dynamic_1 = require("@angular/platform-browser-dynamic");
function AppContext(aModule, selector) {
    var _a;
    var ngModule = {};
    var decorator = aModule.ngModule;
    var AngularComponent = aModule.AngularComponent;
    var zone;
    var componentRef;
    var moduleRef;
    if (AngularComponent) {
        ngModule = {
            imports: [platform_browser_1.BrowserModule],
            declarations: [AngularComponent],
            entryComponents: [AngularComponent]
        };
    }
    else if (decorator == null) {
        aModule = aModule.AngularModule || aModule;
        decorator = findDecorator(aModule);
        if (decorator == null) {
            throw new Error('No NgModule decorator for Angular module');
        }
    }
    Object.assign(ngModule, decorator || {});
    AngularComponent = AngularComponent || ((_a = ngModule.bootstrap) === null || _a === void 0 ? void 0 : _a[0]);
    if (!AngularComponent) {
        throw new Error('No Angular bootstrap component specified');
    }
    delete ngModule.bootstrap;
    function findDecorator(obj) {
        var _a;
        return (_a = obj['__annotations__']) === null || _a === void 0 ? void 0 : _a[0];
    }
    var AppModule = /** @class */ (function () {
        function AppModule(resolver, ngZone) {
            this.resolver = resolver;
            zone = ngZone;
        }
        AppModule.prototype.ngDoBootstrap = function (appRef) {
            var factory = this.resolver.resolveComponentFactory(AngularComponent);
            componentRef = appRef.bootstrap(factory, selector);
        };
        AppModule = __decorate([
            core_1.NgModule(ngModule),
            __metadata("design:paramtypes", [core_1.ComponentFactoryResolver,
                core_1.NgZone])
        ], AppModule);
        return AppModule;
    }());
    AppContext.prototype.isLoaded = function () {
        return moduleRef != null;
    };
    AppContext.prototype.bootstrap = function (compilerOptions) {
        return platform_browser_dynamic_1.platformBrowserDynamic().bootstrapModule(AppModule, compilerOptions).then(function (ref) { return moduleRef = ref; });
    };
    AppContext.prototype.destroy = function () {
        moduleRef === null || moduleRef === void 0 ? void 0 : moduleRef.destroy();
        moduleRef = null;
    };
    AppContext.prototype.invoke = function (functionName, args) {
        return zone.run(function () {
            var instance = componentRef.instance;
            instance[functionName].apply(instance, args);
        });
    };
}
exports.AppContext = AppContext;
//# sourceMappingURL=bootstrap.js.map