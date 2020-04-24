import {ApplicationRef, ComponentFactoryResolver, NgModule, NgModuleRef, NgZone} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

export function AppContext(aModule: any, selector?: string) {

    const appContext = this;
    let ngModule: NgModule = {};
    let extra: NgModule = aModule.ngModule;
    let App = aModule.AngularComponent;

    if (App) {
        ngModule = {
            imports: [BrowserModule],
            declarations: [App],
            entryComponents: [App]
        }
    } else if (!extra) {
        aModule = aModule.AngularModule || aModule;
        extra = findDecorator(aModule);

        if (!extra) {
            throw 'No NgModule decorator for Angular module';
        }
    }

    extra ? Object.assign(ngModule, extra) : null;
    App = App || (ngModule.bootstrap ? ngModule.bootstrap[0] : null);

    if (!App) {
        throw 'No Angular bootstrap target specified';
    }

    delete ngModule.bootstrap;

    function findDecorator(obj: any): any {
        return obj['__annotations__'][0];
    }

    @NgModule(ngModule)
    class AppModule {
        constructor(
            private resolver: ComponentFactoryResolver,
            private ngZone: NgZone
        ) {
            appContext.zone = ngZone;
        }

        ngDoBootstrap(appRef: ApplicationRef) {
            const factory = this.resolver.resolveComponentFactory(App);
            appContext.componentRef = appRef.bootstrap(factory, selector);
        }
    }

    AppContext.prototype.isLoaded = function(): boolean {
        return !!this.moduleRef;
    };

    AppContext.prototype.bootstrap = function(compilerOptions?): Promise<NgModuleRef<AppModule>> {
        const platform = platformBrowserDynamic();
        return platform.bootstrapModule(AppModule, compilerOptions).then(
            ref => this.moduleRef = ref);
    };

    AppContext.prototype.destroy = function(): void {
        this.moduleRef ? this.moduleRef.destroy() : null;
        this.moduleRef = null;
    };

    AppContext.prototype.invoke = function(functionName: string, args: any[]): any {
        return this.zone.run(() => {
            let instance = this.componentRef.instance;
            instance[functionName].apply(instance, args)
        })
    }

}
