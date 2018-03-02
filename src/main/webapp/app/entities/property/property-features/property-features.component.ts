import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../property.model";

export enum Select {
    'YES',
    'NO',
    'UNDEFINED'
}

@Component({
    selector: 'up-property-features',
    templateUrl: './property-features.component.html',
    styles: []
})
export class PropertyFeaturesComponent implements OnInit {

    @Input() property: Property;
    Select = Select;

    constructor() {
    }

    ngOnInit() { }

}
