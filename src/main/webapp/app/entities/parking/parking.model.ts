import { BaseEntity } from './../../shared';

export const enum ParkingType {
    'CAR',
    'MOTO',
    'OTHER'
}

export const enum Select {
    'YES',
    'NO',
    'UNDEFINED'
}

export class Parking implements BaseEntity {
    constructor(
        public id?: number,
        public parkingType?: ParkingType,
        public nearTransport?: Select,
        public surveillance?: Select,
    ) {
    }
}
