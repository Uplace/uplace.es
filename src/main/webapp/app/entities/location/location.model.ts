import { BaseEntity } from './../../shared';

export class Location implements BaseEntity {
    constructor(
        public id?: number,
        public latitude?: number,
        public postalCode?: string,
        public longitude?: number,
        public city?: string,
        public fullAddress?: string,
        public hide?: boolean,
        public urlGMaps?: string,
        public propertyId?: number,
    ) {
        this.hide = false;
    }
}
