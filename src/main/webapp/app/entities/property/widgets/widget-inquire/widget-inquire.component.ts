import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../property.model";

@Component({
    selector: 'up-widget-inquire',
    templateUrl: './widget-inquire.component.html',
    styles: []
})
export class WidgetInquireComponent implements OnInit {

    @Input() property: Property;

    constructor() {
    }

    ngOnInit() {
    }

}
