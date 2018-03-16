import { BaseEntity } from './../../shared';
import {Location} from "../location";

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public photo?: string,
        public phone?: string,
        public email?: string,
        public description?: any,
        public nif?: string,
        public location?: Location,
    ) {
    }
}
