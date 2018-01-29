import { BaseEntity } from './../../shared';

export class Location implements BaseEntity {
    constructor(
        public id?: number,
        public latitude?: string,
        public postalCode?: string,
        public longitude?: string,
        public urlGmaps?: string,
    ) {
    }
}
