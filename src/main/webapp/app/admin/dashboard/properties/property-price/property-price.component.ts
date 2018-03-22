import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Property, TransactionType} from "../../../../entities/property";

@Component({
    selector: 'up-property-price',
    templateUrl: './property-price.component.html',
    styles: []
})
export class PropertyPriceComponent implements OnInit {

    @Input() property: Property;

    @Output() propertyChange: EventEmitter<Property> = new EventEmitter<Property>();

    transactionTypes = TransactionType;

    constructor() {
    }

    ngOnInit() {
    }

}
