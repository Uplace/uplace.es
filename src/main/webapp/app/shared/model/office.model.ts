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

export class Office extends Property {
    constructor(
        public bathrooms?: string,
        public floors?: number,
        public terrace?: Select,
        public office?: Select,
        public storage?: Select,
        public storageSurface?: number,
        public officesSurface?: number,
        public ac?: Select,
        public heat?: Select,
        public energyCertificate?: EnergyCertificate,
    ) {
        super();
    }
}
