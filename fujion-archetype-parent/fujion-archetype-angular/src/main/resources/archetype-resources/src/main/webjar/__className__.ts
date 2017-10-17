import { Component, ViewEncapsulation, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

// Pomodoro timer component
// Note: bootstrapping is handled automatically.
// Note: that we do not specify a selector as it will be assigned during bootstrapping.
@Component({
    moduleId: module.id,
    selector: '${classname}',
    templateUrl: '${className}.html',
    styleUrls: ['${className}.css'],
    encapsulation: ViewEncapsulation.None
})
class ${ClassName} {
}

// Must export component to be bootstrapped as AngularComponent

export { ${ClassName} as AngularComponent };
  
/* Any additional metadata to be passed to the application module during bootstrapping
 * may be exported as in the following example:
 * 
 *      let ngModule : NgModule = { providers: [ AService ] };
 *      export { ngModule };
 * 
 * This will be merged with the default metadata prior to bootstrapping.
*/