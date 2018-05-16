import {Property} from "../../entities/property/index";

export const enum Select {
    YES = 'YES',
    NO = 'No',
    UNDEFINED = 'UNDEFINED'
}

export const enum ApartmentType {
    'HOUSES',
    'RURALS',
    'FLATS',
    'APARTMENTS',
    'TOWERS',
    'LOFTS'
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

export class Apartment extends Property {
    constructor(
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
        super();
        this.numberBedrooms = 0;
        this.numberBathrooms = 0;
        this.m2Edified = 0;
        this.m2Usable = 0;
        this.height = 0;
        this.elevator = Select.UNDEFINED;
        this.heat = Select.UNDEFINED;
        this.parking = Select.UNDEFINED;
        this.terrace = Select.UNDEFINED;
        this.balcony = Select.UNDEFINED;
        this.surfaceTerrace = 0;
        this.surfaceSaloon = 0;
        this.type = ApartmentType.APARTMENTS;
        this.office = Select.UNDEFINED;
        this.ac = Select.UNDEFINED;
    }
}
