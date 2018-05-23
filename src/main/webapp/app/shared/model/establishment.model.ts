import {Property} from "../../entities/property/index";
import {EnergyCertificate} from "./enum/energy-certificate.enum";
import {Select} from "./enum/select.enum";


export const enum UseEstablishment {
    'RESTAURANT',
    'PUB',
    'SHOP',
    'OTHER'
}

export class Establishment extends Property {
    constructor(
        public m2Facade?: number,
        public bathroom?: Select,
        public use?: UseEstablishment,
        public energyCertificate?: EnergyCertificate,
    ) {
        super();
        this.m2Facade = 0;
        this.bathroom = Select.UNDEFINED;
        this.energyCertificate = EnergyCertificate.UNDEFINED;
    }
}
