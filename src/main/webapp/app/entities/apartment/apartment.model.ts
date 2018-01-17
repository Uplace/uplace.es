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

export class Apartment implements BaseEntity {
    constructor(
        public id?: number,
        public numberBedrooms?: number,
        public numberBathrooms?: number,
        public elevator?: Select,
        public ac?: Select,
        public heat?: Select,
        public surfaceTerrace?: number,
        public surfaceSaloon?: number,
        public propertyType?: ApartmentType,
        public office?: Select,
        public kitchenOffice?: Select,
        public storage?: Select,
        public sharedPool?: Select,
        public nearTransport?: Select,
    ) {
    }
}
