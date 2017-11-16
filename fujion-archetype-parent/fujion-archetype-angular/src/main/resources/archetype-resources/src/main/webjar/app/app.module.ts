import { ${ClassName} } from './${className}.component';
import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        FormsModule
    ],
    bootstrap: [${ClassName}],
    entryComponents: [${ClassName}],
    declarations: [${ClassName}]
})
export class AngularModule {
}

