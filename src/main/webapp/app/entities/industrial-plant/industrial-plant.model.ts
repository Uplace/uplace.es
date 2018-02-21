import {Property} from "../property";

export const enum EnergyCertificate {
    'A',
    'B',
    'C',
    'D',
    'E',
    'F',
    'G',
    'UNDEFINED'
}

export class IndustrialPlant extends Property {
    constructor(
        public solarSurface?: number,
        public numberRooms?: number,
        public m2Offices?: number,
        public m2Storage?: number,
        public heightFree?: number,
        public numberDocks?: number,
        public energyCertificate?: EnergyCertificate,
    ) {
        super();
    }
}
