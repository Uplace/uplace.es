import { BaseEntity, User } from './../../shared';

export const enum NotificationType {
    'NOTIFICATION',
    'REQUEST',
    'ALERT'
}

export class Notification implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public content?: any,
        public creation?: any,
        public type?: NotificationType,
        public token?: string,
        public read?: boolean,
        public user?: User,
    ) {
        this.read = false;
    }
}
