import {Property} from "../../entities/property/index";
import {Select} from "./enum/select.enum";

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
        this.numberBathrooms = 0;
        this.elevator = Select.UNDEFINED;
        this.heat = Select.UNDEFINED;
        this.surfaceTerrace = 0;
        this.surfaceGarden = 0;
        this.numberOffice = 0;
        this.surfaceSaloon = 0;
        this.height = 0;
        this.pool = Select.UNDEFINED;
    }
}
