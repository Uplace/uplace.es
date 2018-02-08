import { BaseEntity } from './../../shared';

export class Location implements BaseEntity {
    constructor(
        public id?: number,
        public latitude?: string,
        public postalCode?: string,
        public longitude?: string,
        public fullAddress?: string,
        public hide?: boolean,
        public urlGMaps?: string,
        public property?: BaseEntity,
    ) {
        this.hide = false;
    }
}
