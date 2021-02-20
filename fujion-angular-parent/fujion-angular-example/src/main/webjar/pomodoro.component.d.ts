export declare class PomodoroComponent {
    minutes: number;
    seconds: number;
    isPaused: boolean;
    buttonLabel: string;
    constructor();
    resetPomodoro(): void;
    private tick;
    togglePause(): void;
}
