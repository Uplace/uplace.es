import {Component, Input, OnInit} from '@angular/core';
import {Agent} from "../../../agent";

@Component({
    selector: 'up-widget-agent',
    templateUrl: './widget-agent.component.html',
    styles: []
})
export class WidgetAgentComponent implements OnInit {

    @Input() agents: Agent[];

    constructor() {
    }

    ngOnInit() {
    }

}
