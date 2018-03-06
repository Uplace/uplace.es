import { BaseEntity } from '../../shared/index';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public photo?: string,
        public description?: any,
        public nif?: string,
        public location?: BaseEntity,
    ) {
    }
}
