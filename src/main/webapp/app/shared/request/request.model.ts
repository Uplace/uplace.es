import { BaseEntity } from '../index';
import {Property} from "../../entities/property";

export const enum RequestOrigin {
    'WEB',
    'ANDROID',
    'IOS',
    'OTHER'
}

export const enum RequestStatus {
    'OPEN',
    'CLOSED',
    'PENDING'
}

export class Request implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public date?: any,
        public origin?: RequestOrigin,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public requestStatus?: RequestStatus,
        public message?: any,
        public property?: Property,
    ) {
    }
}
