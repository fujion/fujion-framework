import {ApplicationRef, ComponentRef, NgModuleRef, NgZone} from '@angular/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

export function AppContext(aModule: any, selector?: string) {

    let AngularComponent = aModule.AngularComponent;
    const AngularModule = aModule.AngularModule;

    if (AngularModule == null) {
        throw new Error('No module named "AngularModule" was found');
    }

    const decorator = findDecorator(AngularModule);

    if (AngularComponent == null) {
        AngularComponent = decorator?.bootstrap?.[0];

        if (AngularComponent == null) {
            throw new Error('No Angular bootstrap component specified');
        }

        delete decorator.bootstrap;
    }

    let zone: NgZone;
    let componentRef: ComponentRef<any>;
    let moduleRef: NgModuleRef<any>;

    AngularModule.prototype.ngDoBootstrap = function(appRef: ApplicationRef) {
        //const factory = this.resolver.resolveComponentFactory(AngularComponent);
        componentRef = appRef.bootstrap(AngularComponent, selector);
        zone = componentRef.injector.get(NgZone);
    }

    function findDecorator(obj: any): any {
        return obj.decorators?.[0]?.args?.[0];
    }

    AppContext.prototype.isLoaded = function(): boolean {
        return moduleRef != null;
    };

    AppContext.prototype.bootstrap = function(options?): Promise<NgModuleRef<any>> {
        return platformBrowserDynamic().bootstrapModule(AngularModule, options).then(
            ref => moduleRef = ref);
    };

    AppContext.prototype.destroy = function(): void {
        moduleRef?.destroy();
        moduleRef = null;
    };

    AppContext.prototype.invoke = function(functionName: string, args: any[]): any {
        return zone.run(() => {
            let instance = componentRef.instance;
            instance[functionName].apply(instance, args);
        })
    }

}
