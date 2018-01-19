import { BaseEntity } from './../../shared';

export class Photo implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public photoContentType?: string,
        public photo?: any,
        public gallery?: BaseEntity,
    ) {
    }
}
