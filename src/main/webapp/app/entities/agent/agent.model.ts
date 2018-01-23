import { BaseEntity, User } from './../../shared';

export class Agent implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public phone?: string,
        public photoContentType?: string,
        public photo?: any,
        public user?: User,
        public properties?: BaseEntity[],
    ) {
    }
}
