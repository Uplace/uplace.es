import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Property} from "../../../../entities/property";
import {EnumToArrayPipe} from "../../../../shared/pipes/enumToArray.pipe";
import {Select} from "../../../../shared/model/select.enum";

@Component({
    selector: 'up-property-amenities',
    templateUrl: './property-amenities.component.html',
    styles: []
})
export class PropertyAmenitiesComponent implements OnInit {

    @Input() property: Property;

    @Output() propertyChanged: EventEmitter<Property> = new EventEmitter<Property>();

    Select = Select;

    constructor() {
    }

    ngOnInit() {

    }

}
