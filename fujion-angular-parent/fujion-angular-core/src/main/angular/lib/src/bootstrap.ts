import {ApplicationRef, ComponentRef, DoBootstrap, NgModuleRef, NgZone} from '@angular/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

/**
 * Supports bootstrapping an Angular component.
 *
 * @param aModule The loaded module.
 * @param selector The selector for the DOM element where the Angular component will be attached.
 */
export function AppContext(aModule: any, selector: string) {

    let zone: NgZone;
    let componentRef: ComponentRef<any>;
    let moduleRef: NgModuleRef<any>;

    /**
     * Angular module that will do the bootstrapping.  The decorators of the actual Angular module will be copied
     * here to allow custom bootstrapping logic to be used.
     */
    class AngularModule implements DoBootstrap {

        ngDoBootstrap(appRef: ApplicationRef): void {
            componentRef = appRef.bootstrap(aModule.AngularComponent, selector);
            zone = componentRef.injector.get(NgZone);
        }
    }

    if (aModule.AngularModule == null) {
        throw new Error('No module named "AngularModule" was found');
    }

    // Copy decorators from real Angular module to local module.
    Object.assign(AngularModule, aModule.AngularModule);
    let bootstrapComponent = aModule.AngularModule.ɵmod?.bootstrap?.[0];

    if (bootstrapComponent != null) {
        aModule.AngularModule.ɵmod.bootstrap.length = 0;
    }

    // If an AngularComponent isn't explicitly specified, use the bootstrap component.
    if (aModule.AngularComponent == null) {
        aModule.AngularComponent = bootstrapComponent;

        if (aModule.AngularComponent == null) {
            throw new Error('No Angular bootstrap component specified');
        }
    }

    /**
     * Returns true if the Angular module has been initialized (and, therefore, bootstrapping has occurred).
     */
    AppContext.prototype.isLoaded = function (): boolean {
        return moduleRef != null;
    };

    /**
     * Bootstrap the Angular component.
     */
    AppContext.prototype.bootstrap = function (): Promise<NgModuleRef<any>> {
        return platformBrowserDynamic().bootstrapModule(AngularModule).then(
            ref => moduleRef = ref);
    };

    /**
     * Destroy the module reference.
     */
    AppContext.prototype.destroy = function (): void {
        moduleRef?.destroy();
        moduleRef = null;
    };

    /**
     * Invoke the named function on the bootstrapped component.  The invocation occurs within the component's zone.
     *
     * @param functionName The name of the function to invoke.
     * @param args The function's arguments.
     * @return The result returned by the function.
     */
    AppContext.prototype.invoke = function (functionName: string, args: any[]): any {
        return zone.run(() => {
            let instance = componentRef.instance;
            instance[functionName].apply(instance, args);
        })
    }

}
