import {Directive, ElementRef, Input, OnChanges, Renderer2} from '@angular/core';
import {Property} from "../../entities/property";

@Directive({
    selector: '[property-price]'
})
export class PropertyPriceDirective implements OnChanges {

    @Input() property: Property;
    @Input() currency: string;

    constructor(private _elemRef: ElementRef, private _renderer: Renderer2) {  }

    ngOnChanges(): void {
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
        if (this.isPriceValid(property)) {
            switch (property.transaction) {
                case 'TRANSFER':
                    this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', String(property.priceTransfer + ' ' + this.currency));
                    break;
                case 'RENT_BUY':
                    this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', String(property.priceRent + ' - ' + property.priceSell + ' €'));
                    break;
                case 'BUY':
                    this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', String(property.priceSell + ' ' + this.currency));
                    break;
                case 'RENT':
                    this._renderer.setProperty(this._elemRef.nativeElement, 'innerHTML', String(property.priceRent + ' ' + this.currency));
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
                if (property.priceTransfer) return true;
                break;
            case 'RENT_BUY':
                if (property.priceSell && property.priceRent) return true;
                break;
            case 'BUY':
                if (property.priceSell) return true;
                break;
            case 'RENT':
                if (property.priceRent) return true;
                break;
            default:
                return false;
        }
    }

}
