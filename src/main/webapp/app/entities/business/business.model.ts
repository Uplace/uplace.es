import {Property} from "../property";

export const enum BusinessType {
    'PUB',
    'HOTEL',
    'OTHERS'
}

export const enum Select {
    'YES',
    'NO',
    'UNDEFINED'
}

export class Business extends Property {
    constructor(
        public type?: BusinessType,
        public numberBathrooms?: number,
        public elevator?: Select,
        public ac?: Select,
        public heat?: Select,
        public surfaceTerrace?: number,
        public surfaceGarden?: number,
        public numberOffice?: number,
        public surfaceSaloon?: number,
        public height?: number,
        public pool?: Select,
    ) {
        super();
    }
}
