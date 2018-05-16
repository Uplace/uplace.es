import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Property} from "../../../../entities/property";
import {Select} from "../../../../shared/model/enum/select.enum";
import 'reflect-metadata'
import {Apartment} from "../../../../shared/model/apartment.model";

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
