import {Property} from "../../entities/property/index";
import {EnergyCertificate} from "./enum/energy-certificate.enum";

export class IndustrialPlant extends Property {
    constructor(
        public solarSurface?: number,
        public numberRooms?: number,
        public m2Offices?: number,
        public m2Storage?: number,
        public heightFree?: number,
        public numberDocks?: number,
        public energyCertificate?: EnergyCertificate,
    ) {
        super();
        this.solarSurface = 0;
        this.numberRooms = 0;
        this.m2Offices = 0;
        this.m2Storage = 0;
        this.heightFree = 0;
        this.numberDocks = 0;
        this.energyCertificate = EnergyCertificate.UNDEFINED;
    }
}
