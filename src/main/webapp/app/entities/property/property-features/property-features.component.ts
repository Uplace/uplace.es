import {Component, Input, OnInit} from '@angular/core';
import {Property} from "../property.model";
import {Select} from "../../../shared/model/select.enum";

@Component({
    selector: 'up-property-features',
    templateUrl: './property-features.component.html',
    styles: []
})
export class PropertyFeaturesComponent implements OnInit {

    @Input() property: Property;
    Select = Select;
    excludedAttributesProperty = ['id', 'title', 'created', 'updated', 'description', 'transaction', 'reference', 'priceSell', 'priceRent', 'priceTransfer', 'yearConstruction', 'visible', 'surface', 'requests', 'photos', 'propertyType', 'realEstate'];
    excludedAttributes = [];

    constructor() {
        this.excludedAttributes.push(...this.excludedAttributesProperty);
    }

    ngOnInit() {
    }

    getKeys(property): string[] {
        return Object.keys(property).filter(attribute => !this.excludedAttributes.includes(attribute));
    }

}
