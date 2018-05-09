import {Directive, ElementRef, Input, OnInit, Renderer2} from '@angular/core';
import {Property} from "../../entities/property";
import {s} from "@angular/core/src/render3";
import {el} from "@angular/platform-browser/testing/src/browser_util";

@Directive({
    selector: '[property-price]'
})
export class PropertyPriceDirective implements OnInit {

    @Input() property: Property;
    @Input() currency: string;

    constructor(private _elemRef: ElementRef, private _renderer: Renderer2) {
    }

    ngOnInit(): void {
        if (!this.currency) {
            this.currency = '€';
        }
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
            switch (property.transaction) {
                case 'TRANSFER':
                    this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', String(property.priceTransfer + this.currency));
                    break;
                case 'RENT_BUY':
                    this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', String(property.priceRent + ' - ' + property.priceSell));
                    break;
                case 'BUY':
                    this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', String(property.priceSell + this.currency));
                    break;
                case 'RENT':
                    this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', String(property.priceRent + this.currency));
                    break;
                default:
                    this._renderer.setStyle(this._elemRef.nativeElement, 'display', 'none');
                    break;
            }
        } else {
            this._renderer.setStyle(this._elemRef.nativeElement, 'display', 'none');
        }
    }

    private isPriceValid(property: Property): boolean {
        switch (property.transaction) {
            case 'TRANSFER':
                console.log(property.transaction + ' is ' + property.priceTransfer);
                if (property.priceTransfer) return true;
                break;
            case 'RENT_BUY':
                console.log(property.transaction + ' is ' + property.priceRent + ' - ' + property.priceSell);
                if (property.priceSell && property.priceRent) return true;
                break;
            case 'BUY':
                console.log(property.transaction + ' is ' + property.priceSell);
                if (property.priceSell) return true;
                break;
            case 'RENT':
                console.log(property.transaction + ' is ' + property.priceRent);
                if (property.priceRent) return true;
                break;
            default:
                return false;
        }
    }

}
