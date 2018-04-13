import {Property} from "../../entities/property/index";

export const enum Select {
    'YES',
    'NO',
    'UNDEFINED'
}

export const enum BusinessType {
    'PUB',
    'HOTEL',
    'OTHERS'
}

export class Business extends Property {
    constructor(
        public numberBathrooms?: number,
        public elevator?: Select,
        public ac?: Select,
        public heat?: Select,
        public surfaceTerrace?: number,
        public surfaceGarden?: number,
        public type?: BusinessType,
        public numberOffice?: number,
        public surfaceSaloon?: number,
        public height?: number,
        public pool?: Select
    ) {
        super();
    }
}
