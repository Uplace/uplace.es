import { BaseEntity } from './../../shared';

export class Gallery implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public photos?: BaseEntity[],
        public propertyId?: number,
    ) {
    }
}
