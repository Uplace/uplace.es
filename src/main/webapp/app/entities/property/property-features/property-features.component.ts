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
        return Object.keys(property);
        /**
         * Implement this on a method and filter by the excluded arrays
         * */
        /*return Object.keys(property).filter(key => {
            this.excludedAttributes.forEach((attribute) => {
                if (key === attribute) {
                    console.log('key ' + key + ' excluded');
                    return key;
                }
            })
        })*/
    }

}
