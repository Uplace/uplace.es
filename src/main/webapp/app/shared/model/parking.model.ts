import {Property} from "../../entities/property/index";
import {Select} from "./enum/select.enum";

export const enum ParkingType {
    'CAR',
    'MOTO',
    'OTHER'
}

export class Parking extends Property {
    constructor(
        public parkingType?: ParkingType,
        public nearTransport?: Select,
        public surveillance?: Select,
    ) {
        super();
        this.nearTransport = Select.UNDEFINED;
        this.surveillance = Select.UNDEFINED;
    }
}
