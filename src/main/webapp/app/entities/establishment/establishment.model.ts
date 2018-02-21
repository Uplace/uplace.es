import { BaseEntity } from './../../shared';
import {Property} from "../property";

export const enum Select {
    'YES',
    'NO',
    'UNDEFINED'
}

export const enum UseEstablishment {
    'RESTAURANT',
    'PUB',
    'SHOP',
    'OTHER'
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

export class Establishment extends Property implements BaseEntity {
    constructor(
        public id?: number,
        public m2Facade?: number,
        public bathroom?: Select,
        public use?: UseEstablishment,
        public energyCertificate?: EnergyCertificate,
    ) {
        super();
    }
}
