import {
    AfterViewChecked, AfterViewInit, Component, EventEmitter, Input, OnChanges, OnInit,
    Output, SimpleChanges
} from '@angular/core';
import {Property, TransactionType} from "../../../../entities/property";
import {NgForm} from "@angular/forms";

@Component({
    selector: 'up-property-price',
    templateUrl: './property-price.component.html',
    styles: []
})
export class PropertyPriceComponent implements OnInit, AfterViewInit, OnChanges {

    @Input('parentForm') newPropertyForm: NgForm;

    @Input() property: Property = new Property();

    @Output() propertyChange: EventEmitter<Property> = new EventEmitter<Property>();

    transactionTypes = TransactionType;

    constructor() { }

    ngOnChanges(changes: SimpleChanges) {

        if (changes['property'].currentValue.transaction) {
            console.log('defined');
            console.log(changes['property'])
        } else {
            console.log('undefined');
            this.property.transaction = null
        }

    }

    ngAfterViewInit(): void {
        /*// console.log(this.property.transaction);
        if (this.property.transaction == undefined) {
            console.log('undefined');
            // this.property.transaction = this.transactionTypes.RENT;
            // console.log(this.property.transaction)
        } else {
            console.log(this.property.transaction);
            console.log('not undefined');
            // console.log(this.transactionTypes[this.property.transaction]);
        }*/
    }


    ngOnInit() {
        this.propertyChange.subscribe((property) => {
            console.log(property);
        })
    }

}
