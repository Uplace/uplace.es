import { BaseEntity } from './../../shared';

export const enum Select {
    'YES',
    'NO',
    'UNDEFINED'
}

export const enum ApartmentType {
    'HOUSE',
    'RURAL',
    'FLAT',
    'APARTMENT',
    'TOWER',
    'LOFT'
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

export class Apartment implements BaseEntity {
    constructor(
        public id?: number,
        public numberBedrooms?: number,
        public numberBathrooms?: number,
        public m2Edified?: number,
        public m2Usable?: number,
        public height?: number,
        public elevator?: Select,
        public ac?: Select,
        public heat?: Select,
        public parking?: Select,
        public terrace?: Select,
        public balcony?: Select,
        public surfaceTerrace?: number,
        public surfaceSaloon?: number,
        public type?: ApartmentType,
        public office?: Select,
        public kitchenOffice?: Select,
        public storage?: Select,
        public sharedPool?: Select,
        public nearTransport?: Select,
        public reformed?: Select,
        public energyCertificate?: EnergyCertificate,
        public certificateHabitability?: Select,
    ) {
    }
}
