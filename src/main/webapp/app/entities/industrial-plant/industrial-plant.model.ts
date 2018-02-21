import { BaseEntity } from './../../shared';
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

export class IndustrialPlant extends Property implements BaseEntity {
    constructor(
        public id?: number,
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
