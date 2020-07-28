import {ApplicationRef, ComponentFactoryResolver, DoBootstrap, NgModule, NgModuleRef, NgZone} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

export function AppContext(aModule: any, selector?: string) {

    const appContext = this;
    let ngModule: NgModule = {};
    let extra: NgModule = aModule.ngModule;
    let AngularComponent = aModule.AngularComponent;

    if (AngularComponent) {
        ngModule = {
            imports: [BrowserModule],
            declarations: [AngularComponent],
            entryComponents: [AngularComponent]
        }
    } else if (!extra) {
        aModule = aModule.AngularModule || aModule;
        extra = findDecorator(aModule);

        if (!extra) {
            throw 'No NgModule decorator for Angular module';
        }
    }

    extra ? Object.assign(ngModule, extra) : null;
    AngularComponent = AngularComponent || ngModule.bootstrap?.[0];

    if (!AngularComponent) {
        throw 'No Angular bootstrap target specified';
    }

    delete ngModule.bootstrap;

    function findDecorator(obj: any): any {
        return obj['__annotations__']?.[0];
    }

    @NgModule(ngModule)
    class AppModule implements DoBootstrap {
        constructor(
            private readonly resolver: ComponentFactoryResolver,
            private readonly ngZone: NgZone
        ) {
            appContext.zone = ngZone;
        }

        ngDoBootstrap(appRef: ApplicationRef) {
            const factory = this.resolver.resolveComponentFactory(AngularComponent);
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
