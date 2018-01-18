import { BaseEntity } from './../../shared';

export class Property implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public price?: number,
        public created?: any,
        public updated?: any,
        public description?: any,
        public reference?: string,
        public priceSell?: number,
        public priceRent?: number,
        public yearConstruction?: number,
        public newCreation?: boolean,
        public visible?: boolean,
        public surface?: number,
        public gallery?: BaseEntity,
        public manageds?: BaseEntity[],
    ) {
        this.newCreation = false;
        this.visible = false;
    }
}
