import {ApplicationRef, ComponentFactoryResolver, DoBootstrap, NgModule, NgModuleRef, NgZone} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

export function AppContext(aModule: any, selector?: string) {

    const appContext = this;
    let ngModule: NgModule = {};
    let decorator: NgModule = aModule.ngModule;
    let AngularComponent = aModule.AngularComponent;

    if (AngularComponent) {
        ngModule = {
            imports: [BrowserModule],
            declarations: [AngularComponent],
            entryComponents: [AngularComponent]
        }
    } else if (decorator == null) {
        aModule = aModule.AngularModule || aModule;
        decorator = findDecorator(aModule);

        if (decorator == null) {
            throw new Error('No NgModule decorator for Angular module');
        }
    }

    Object.assign(ngModule, decorator || {});
    AngularComponent = AngularComponent || ngModule.bootstrap?.[0];

    if (!AngularComponent) {
        throw new Error('No Angular bootstrap component specified');
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
        return this.moduleRef != null;
    };

    AppContext.prototype.bootstrap = function(compilerOptions?): Promise<NgModuleRef<AppModule>> {
        return platformBrowserDynamic().bootstrapModule(AppModule, compilerOptions).then(
            ref => this.moduleRef = ref);
    };

    AppContext.prototype.destroy = function(): void {
        this.moduleRef?.destroy();
        this.moduleRef = null;
    };

    AppContext.prototype.invoke = function(functionName: string, args: any[]): any {
        return this.zone.run(() => {
            let instance = this.componentRef.instance;
            instance[functionName].apply(instance, args)
        })
    }

}
