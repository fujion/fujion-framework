import {ApplicationRef, ComponentRef, NgModuleRef, NgZone} from '@angular/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

interface Context {
    selector?: string;
    zone?: NgZone;
    componentRef?: ComponentRef<any>;
    moduleRef?: NgModuleRef<any>;
}
/**
 * Supports bootstrapping an Angular component.
 *
 * @param aModule The loaded module.
 * @param selector The selector for the DOM element where the Angular component will be attached.
 */
export function AppContext(aModule: any, selector: string) {

    this.context = <Context> {selector};

    if (aModule.fujion != null) {
        return;
    }

    aModule.fujion = {
        contexts: []
    };

    const AngularModule = aModule.AngularModule;

    if (AngularModule == null) {
        throw new Error('No module named "AngularModule" was found');
    }

    AngularModule.prototype.ngDoBootstrap = function (appRef: ApplicationRef): void {
        const context = <Context> aModule.fujion.contexts.shift();
        context.componentRef = appRef.bootstrap(aModule.AngularComponent, context.selector);
        context.zone = context.componentRef.injector.get(NgZone);
    }

    let bootstrapComponent = AngularModule.ɵmod?.bootstrap?.[0];

    if (bootstrapComponent != null) {
        AngularModule.ɵmod.bootstrap.length = 0;
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
        return this.context.moduleRef != null;
    };

    /**
     * Bootstrap the Angular component.
     */
    AppContext.prototype.bootstrap = function (): Promise<NgModuleRef<any>> {
        aModule.fujion.contexts.push(this.context);
        return platformBrowserDynamic().bootstrapModule(AngularModule).then(
            ref => this.context.moduleRef = ref);
    };

    /**
     * Destroy the module reference.
     */
    AppContext.prototype.destroy = function (): void {
        this.context?.moduleRef?.destroy();
        this.context = null;
    };

    /**
     * Invoke the named function on the bootstrapped component.  The invocation occurs within the component's zone.
     *
     * @param functionName The name of the function to invoke.
     * @param args The function's arguments.
     * @return The result returned by the function.
     */
    AppContext.prototype.invoke = function (functionName: string, args: any[]): any {
        return this.context.zone.run(() => {
            let instance = this.context.componentRef.instance;
            instance[functionName].apply(instance, args);
        })
    }

}
