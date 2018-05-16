import {Property} from "../../entities/property/index";
import {Select} from "./enum/select.enum";
import {EnergyCertificate} from "./enum/energy-certificate.enum";

export class Hotel extends Property {
    constructor(
        public solarSurface?: number,
        public m2Edified?: number,
        public numberRooms?: number,
        public operator?: Select,
        public pool?: Select,
        public spa?: Select,
        public conferenceRoom?: Select,
        public floorsSR?: number,
        public floorsBR?: number,
        public energyCertificate?: EnergyCertificate,
    ) {
        super();
        this.solarSurface = 0;
        this.m2Edified = 0;
        this.numberRooms = 0;
        this.operator = Select.UNDEFINED;
        this.pool = Select.UNDEFINED;
        this.spa = Select.UNDEFINED;
        this.conferenceRoom = Select.UNDEFINED;
        this.floorsSR = 0;
        this.floorsBR = 0;
        this.energyCertificate = EnergyCertificate.UNDEFINED;
    }
}
