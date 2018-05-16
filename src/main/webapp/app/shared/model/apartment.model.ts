import {Property} from "../../entities/property";
import {EnergyCertificate} from "./enum/energy-certificate.enum";
import {Select} from "./enum/select.enum";



export const enum ApartmentType {
    'HOUSES',
    'RURALS',
    'FLATS',
    'APARTMENTS',
    'TOWERS',
    'LOFTS'
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
        this.kitchenOffice = Select.UNDEFINED;
        this.storage = Select.UNDEFINED;
        this.sharedPool = Select.UNDEFINED;
        this.nearTransport = Select.UNDEFINED;
        this.reformed = Select.UNDEFINED;
        this.energyCertificate = EnergyCertificate.UNDEFINED;
        this.certificateHabitability = Select.UNDEFINED;
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
