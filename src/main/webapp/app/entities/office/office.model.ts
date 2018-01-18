import { BaseEntity } from './../../shared';

export const enum Select {
    'YES',
    'NO',
    'UNDEFINED'
}

export class Office implements BaseEntity {
    constructor(
        public id?: number,
        public bathrooms?: string,
        public floors?: number,
        public terrace?: number,
        public office?: Select,
        public storage?: Select,
        public storageSurface?: number,
        public officesSurface?: number,
        public ac?: Select,
        public heat?: Select,
    ) {
    }
}
