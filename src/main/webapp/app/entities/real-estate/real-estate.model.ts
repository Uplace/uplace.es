import { BaseEntity } from './../../shared';

export class RealEstate implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public nif?: string,
        public reference?: string,
        public properties?: BaseEntity[],
    ) {
    }
}
