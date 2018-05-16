import {Property} from "../../entities/property/index";
import {Select} from "./enum/select.enum";
import {EnergyCertificate} from "./enum/energy-certificate.enum";

export class Office extends Property {
    constructor(
        public bathrooms?: number,
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
        this.bathrooms = 0;
        this.floors = 0;
        this.terrace = Select.UNDEFINED;
        this.office = Select.UNDEFINED;
        this.storage = Select.UNDEFINED;
        this.storageSurface = 0;
        this.officesSurface = 0;
        this.ac = Select.UNDEFINED;
        this.heat = Select.UNDEFINED;
        this.energyCertificate = EnergyCertificate.UNDEFINED;
    }
}
