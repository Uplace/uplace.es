import { BaseEntity } from '../../shared/index';
import {Location} from "../location";

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public photo?: string,
        public description?: any,
        public nif?: string,
        public location?: Location,
    ) {
    }
}
