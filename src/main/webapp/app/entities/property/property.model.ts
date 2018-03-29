import { BaseEntity } from './../../shared';
import {Photo} from "../photo";
import {Agent} from "../agent";
import {Location} from "../location/location.model";
import {RealEstate} from "../real-estate";

export enum TransactionType {
    RENT = 0,
    BUY = 1,
    TRANSFER = 2,
    RENT_BUY = 3
}

export class Property implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public created?: any,
        public updated?: any,
        public propertyType?: string,
        public description?: any,
        public transaction?: any,
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
        public requests?: Request[],
        public realEstate?: RealEstate
    ) {
        this.priceRent = 0;
        this.priceSell = 0;
        this.priceTransfer = 0;
        this.surface = 0;
        this.yearConstruction = 500;
        this.newCreation = false;
        this.visible = false;
        this.location = new Location();
    }
}
