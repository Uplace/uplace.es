import { BaseEntity } from './../../shared';

export const enum TransactionType {
    'RENT',
    'BUY',
    'TRANSFER',
    'RENT_BUY'
}

export class Property implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public price?: number,
        public created?: any,
        public updated?: any,
        public description?: any,
        public transaction?: TransactionType,
        public reference?: string,
        public priceSell?: number,
        public priceRent?: number,
        public yearConstruction?: number,
        public newCreation?: boolean,
        public visible?: boolean,
        public surface?: number,
        public location?: BaseEntity,
        public photos?: BaseEntity[],
        public managers?: BaseEntity[],
    ) {
        this.newCreation = false;
        this.visible = false;
    }
}
