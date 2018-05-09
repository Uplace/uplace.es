import {Directive, ElementRef, Input, OnInit, Renderer2} from '@angular/core';
import {Property} from "../../entities/property";
import {s} from "@angular/core/src/render3";
import {el} from "@angular/platform-browser/testing/src/browser_util";

@Directive({
    selector: '[property-price]'
})
export class PropertyPriceDirective implements OnInit {

    @Input() property: Property;

    constructor(private _elemRef: ElementRef, private _renderer: Renderer2) {
    }

    ngOnInit(): void {
        this.setPrice(this.property);
        /*switch (this.property.transaction) {
            case 'RENT':
                this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', this.property.priceRent);
                break;
            case 'BUY':
                this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', this.property.priceSell);
                break;
            case 'TRANSFER':
                this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', this.property.priceTransfer);
                break;
            case 'RENT_BUY':
                this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', this.property.priceRent + ' - ' + this.property.priceSell);
                break;
        }*/
    }

    private setPrice(property: Property) {
        console.log(property.title + ' - ' + property.transaction);
        // this._renderer.setStyle(this._elemRef.nativeElement, 'display', 'none');
        if (this.isPriceValid(property)) {
            if (property.transaction === 'RENT_BUY') {
                this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', property.priceRent + ' - ' + property.priceSell);
            } else {
                this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', property.priceTransfer);
            }
        } else {
            this._renderer.setStyle(this._elemRef.nativeElement, 'display', 'none');
        }
    }

    private isPriceValid(property: Property): boolean {
        switch (property.transaction) {
            case 'TRANSFER':
                if (property.priceTransfer) return true;
                break;
            case 'RENT_BUY':
                if (property.priceSell || property.priceRent) return true;
                break;
            default: return false;
        }
    }

}
