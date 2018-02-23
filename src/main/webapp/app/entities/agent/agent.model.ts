import { BaseEntity } from './../../shared';

export class Agent implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public phone?: string,
        public photoContentType?: string,
        public photo?: any,
        public userId?: number,
        public properties?: BaseEntity[],
    ) {
    }
}
