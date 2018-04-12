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
    excludedAttributes = ['requests', 'photos', 'priceTransfer', 'priceSell', 'surface', 'id', 'reference'];

    constructor() {
    }

    ngOnInit() {

    }

    getKeys(property): string[] {
        return Object.keys(property).map((key) => {
            if (key != this.excludedAttributes[0]){
                return key;
            }
        });
    }

}
