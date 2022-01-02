import {ApplicationRef, ComponentRef, NgModuleRef, NgZone} from '@angular/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

export function AppContext(aModule: any, selector: string) {

    this.selector = selector;
    let zone: NgZone;
    let componentRef: ComponentRef<any>;
    let moduleRef: NgModuleRef<any>;

    if (aModule.fujion == null) {
        let AngularComponent = aModule.AngularComponent;
        const AngularModule = aModule.AngularModule;

        if (AngularModule == null) {
            throw new Error('No module named "AngularModule" was found');
        }

        if (AngularComponent == null) {
            const decorator = findDecorator(AngularModule);
            AngularComponent = decorator?.bootstrap?.[0];

            if (AngularComponent == null) {
                throw new Error('No Angular bootstrap component specified');
            }

            delete decorator.bootstrap;
        }

        AngularModule.prototype.ngDoBootstrap = (appRef: ApplicationRef) => {
            const context = aModule.fujion.contexts.shift();
            context.componentRef = appRef.bootstrap(AngularComponent, context.selector);
            context.zone = context.componentRef.injector.get(NgZone);
        }

        aModule.fujion = {
            AngularComponent,
            AngularModule,
            contexts: []
        }

        function findDecorator(obj: any): any {
            return obj.decorators?.[0]?.args?.[0];
        }
    }

    AppContext.prototype.isLoaded = function (): boolean {
        return moduleRef != null;
    };

    AppContext.prototype.bootstrap = function (): Promise<NgModuleRef<any>> {
        aModule.fujion.contexts.push(this);
        return platformBrowserDynamic().bootstrapModule(aModule.fujion.AngularModule).then(
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
