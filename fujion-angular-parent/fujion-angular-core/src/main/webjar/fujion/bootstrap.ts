import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {ApplicationRef, ComponentFactory, ComponentFactoryResolver, NgModuleRef, NgZone, ComponentRef} from '@angular/core';

export function AppContext(aModule: any, selector?: string) {

    var appContext = this;
    var ngModule: NgModule = {};
    var extra: NgModule = aModule.ngModule;
    var App = aModule.AngularComponent;

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
        var metadata = (<any>Reflect).getMetadata('annotations', obj);

        if (metadata) {
            for (let md of metadata) {
                if (md.toString() === '@NgModule') {
                    return md;
                }
            }
        }

        return null;
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
    }

    AppContext.prototype.bootstrap = function(compilerOptions?): Promise<NgModuleRef<AppModule>> {
        const platform = platformBrowserDynamic();
        return platform.bootstrapModule(AppModule, compilerOptions).then(
            ref => this.moduleRef = ref);
    }

    AppContext.prototype.destroy = function(): void {
        this.moduleRef ? this.moduleRef.destroy() : null;
        this.moduleRef = null;
    }

    AppContext.prototype.invoke = function(functionName: string, args: any[]): any {
        return this.zone.run(() => {
            let instance = this.componentRef.instance;
            instance[functionName].apply(instance, args)
        })
    }

}