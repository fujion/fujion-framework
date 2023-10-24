import {PomodoroComponent} from './pomodoro.component';
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

@NgModule({
    imports: [BrowserModule],
    bootstrap: [PomodoroComponent],
    declarations: [PomodoroComponent]
})

export class AngularModule {
}

