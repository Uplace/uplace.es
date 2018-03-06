import {Property} from "../../entities/property/index";

export const enum Select {
    'YES',
    'NO',
    'UNDEFINED'
}

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

export class Hotel extends Property {
    constructor(
        public solarSurface?: number,
        public m2Edified?: number,
        public numberRooms?: number,
        public operator?: Select,
        public pool?: Select,
        public spa?: Select,
        public conferenceRoom?: Select,
        public floorsSR?: number,
        public floorsBR?: number,
        public energyCertificate?: EnergyCertificate,
    ) {
        super();
    }
}
