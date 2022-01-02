import {ApplicationRef, ComponentRef, DoBootstrap, NgModuleRef, NgZone} from '@angular/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

export function AppContext(aModule: any, selector: string) {

    let zone: NgZone;
    let componentRef: ComponentRef<any>;
    let moduleRef: NgModuleRef<any>;

    class AngularModule implements DoBootstrap {
        static decorators: any;

        ngDoBootstrap(appRef: ApplicationRef): void {
            componentRef = appRef.bootstrap(aModule.AngularComponent, selector);
            zone = componentRef.injector.get(NgZone);
        }
    }

    if (aModule.AngularModule == null) {
        throw new Error('No module named "AngularModule" was found');
    }

    const decorators = aModule.AngularModule.decorators;
    AngularModule.decorators = decorators;
    const ngModule = decorators?.[0]?.args?.[0];

    if (aModule.AngularComponent == null) {
        aModule.AngularComponent = ngModule?.bootstrap?.[0];

        if (aModule.AngularComponent == null) {
            throw new Error('No Angular bootstrap component specified');
        }
    }

    delete ngModule.bootstrap;

    AppContext.prototype.isLoaded = function (): boolean {
        return moduleRef != null;
    };

    AppContext.prototype.bootstrap = function (): Promise<NgModuleRef<any>> {
        return platformBrowserDynamic().bootstrapModule(AngularModule).then(
            ref => moduleRef = ref);
    };

    AppContext.prototype.destroy = function (): void {
        moduleRef?.destroy();
        moduleRef = null;
    };

    AppContext.prototype.invoke = function (functionName: string, args: any[]): any {
        return zone.run(() => {
            let instance = componentRef.instance;
            instance[functionName].apply(instance, args);
        })
    }

}
