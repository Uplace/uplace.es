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

export class Parking extends Property {
    constructor(
        public parkingType?: ParkingType,
        public nearTransport?: Select,
        public surveillance?: Select,
    ) {
        super();
    }
}
