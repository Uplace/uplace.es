import { BaseEntity } from './../../shared';
import {Property} from "../property";

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

export class Parking extends Property implements BaseEntity {
    constructor(
        public parkingType?: ParkingType,
        public nearTransport?: Select,
        public surveillance?: Select,
    ) {
        super();
    }
}
