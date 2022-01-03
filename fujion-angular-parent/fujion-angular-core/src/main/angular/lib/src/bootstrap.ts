import {ApplicationRef, ComponentRef, NgModuleRef, NgZone} from '@angular/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

/**
 * Supports bootstrapping an Angular component.
 */
export class AppContext {

    private zone: NgZone;

    private componentRef: ComponentRef<any>;

    private moduleRef: NgModuleRef<any>;

    private readonly aModule: any;

    get contexts(): AppContext[] {
        return this.aModule._fujion.contexts;
    }

    /**
     * @param aModule The loaded library module.
     * @param selector The selector for the DOM element where the Angular component will be attached.
     */
    constructor(aModule: any, readonly selector: string) {
        this.aModule = aModule;
        this.init();
    }

    /**
     * Initializes the module if not already done.
     */
    private init(): void {
        if (this.aModule._fujion != null) {
            return;
        }

        this.aModule._fujion = {
            contexts: <AppContext[]>[]
        };

        const AngularModule = this.aModule.AngularModule;

        if (AngularModule == null) {
            throw new Error('No module named "AngularModule" was found');
        }

        // Add custom bootstrap logic to module.
        AngularModule.prototype.ngDoBootstrap = (appRef: ApplicationRef) => {
            const context = this.contexts.shift();
            context.componentRef = appRef.bootstrap(this.aModule.AngularComponent, context.selector);
            context.zone = context.componentRef.injector.get(NgZone);
        }

        // Check for a bootstrap component in the module decorator.
        let bootstrapComponent = AngularModule.ɵmod?.bootstrap?.[0];

        // If a bootstrap component was found, remove it from the decorator so custom bootstrap logic will run.
        if (bootstrapComponent != null) {
            AngularModule.ɵmod.bootstrap.length = 0;
        }

        // If an AngularComponent isn't explicitly specified, use the bootstrap component.
        if (this.aModule.AngularComponent == null) {
            this.aModule.AngularComponent = bootstrapComponent;

            if (this.aModule.AngularComponent == null) {
                throw new Error('No Angular bootstrap component specified');
            }
        }
    }

    /**
     * Returns true if the Angular module has been initialized (and, therefore, bootstrapping has occurred).
     */
    isLoaded(): boolean {
        return this.moduleRef != null;
    };

    /**
     * Bootstrap the Angular component.
     */
    bootstrap(): Promise<NgModuleRef<any>> {
        this.contexts.push(this);
        return platformBrowserDynamic().bootstrapModule(this.aModule.AngularModule).then(
            ref => this.moduleRef = ref);
    };

    /**
     * Destroy the module and component references.
     */
    destroy(): void {
        this.moduleRef?.destroy();
        this.componentRef?.destroy();
        this.moduleRef = null;
        this.componentRef = null;
        this.zone = null;
    };

    /**
     * Invoke the named function on the bootstrapped component.  The invocation occurs within the component's zone.
     *
     * @param functionName The name of the function to invoke.
     * @param args The function's arguments.
     * @return The result returned by the function.
     */
    invoke(functionName: string, args: any[]): any {
        return this.zone.run(() => {
            let instance = this.componentRef.instance;
            instance[functionName].apply(instance, args);
        })
    }

}
