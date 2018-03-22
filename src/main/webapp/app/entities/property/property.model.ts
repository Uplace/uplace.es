import { BaseEntity } from './../../shared';
import {Photo} from "../photo";
import {Agent} from "../agent";
import {Location} from "../location/location.model";
import {RealEstate} from "../real-estate";

export enum TransactionType {
    RENT,
    BUY,
    TRANSFER,
    RENT_BUY
}

export class Property implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public created?: any,
        public updated?: any,
        public propertyType?: string,
        public description?: any,
        public transaction?: TransactionType,
        public reference?: string,
        public priceSell?: number,
        public priceRent?: number,
        public priceTransfer?: number,
        public yearConstruction?: number,
        public newCreation?: boolean,
        public visible?: boolean,
        public surface?: number,
        public location?: Location,
        public photos?: Photo[],
        public managers?: Agent[],
        public realEstate?: RealEstate
    ) {
        this.newCreation = false;
        this.visible = false;
        this.location = new Location();
    }
}
