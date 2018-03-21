import { BaseEntity } from './../../shared';
import {Property} from "../property";

export class RealEstate implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public nif?: string,
        public reference?: string,
        public properties?: Property[],
    ) {
    }
}
