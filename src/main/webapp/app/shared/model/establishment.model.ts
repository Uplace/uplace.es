import {Property} from "../../entities/property/index";

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

export class Establishment extends Property {
    constructor(
        public m2Facade?: number,
        public bathroom?: Select,
        public use?: UseEstablishment,
        public energyCertificate?: EnergyCertificate,
    ) {
        super();
    }
}
