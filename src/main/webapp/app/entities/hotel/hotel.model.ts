import { BaseEntity } from './../../shared';

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

export class Hotel implements BaseEntity {
    constructor(
        public id?: number,
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
    }
}
