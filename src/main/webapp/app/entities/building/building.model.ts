import {Property} from "../property";

export const enum BuildingType {
    'RESIDENTIAL',
    'HOTEL',
    'INDUSTRIAL'
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

export class Building extends Property {
    constructor(
        public type?: BuildingType,
        public solarSurface?: number,
        public m2Edified?: number,
        public floorsSR?: number,
        public floorsBR?: number,
        public energyCertificate?: EnergyCertificate,
    ) {
        super();
    }
}
