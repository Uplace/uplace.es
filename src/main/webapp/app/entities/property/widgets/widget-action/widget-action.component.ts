import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../../property.model";

@Component({
  selector: 'up-widget-action',
  templateUrl: './widget-action.component.html',
  styles: []
})
export class WidgetActionComponent implements OnInit {

    @Input() property: Property;

    constructor() { }

    ngOnInit() {
    }

}
