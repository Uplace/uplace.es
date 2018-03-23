import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Property, TransactionType} from "../../../../entities/property";
import {NgForm} from "@angular/forms";
import {KeysPipe} from "../../../../shared/pipes/keys.pipe";

@Component({
    selector: 'up-property-price',
    templateUrl: './property-price.component.html',
    styles: []
})
export class PropertyPriceComponent implements OnInit {

    @Input('parentForm') newPropertyForm: NgForm;

    @Input() property: Property = new Property();

    @Output() propertyChange: EventEmitter<Property> = new EventEmitter<Property>();

    transactionTypes = TransactionType;

    constructor() {}

    ngOnInit() {}

}
