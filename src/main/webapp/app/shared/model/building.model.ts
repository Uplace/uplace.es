import {Property} from "../../entities/property/index";
import {EnergyCertificate} from "./enum/energy-certificate.enum";

export const enum BuildingType {
    'RESIDENTIAL',
    'HOTEL',
    'INDUSTRIAL'
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
        this.solarSurface = 0;
        this.m2Edified = 0;
        this.floorsBR = 0;
        this.floorsSR = 0;
    }
}
