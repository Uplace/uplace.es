import { BaseEntity } from './../../shared';

export const enum BussinessType {
    'PUB',
    'HOTEL',
    'OTHERS'
}

export const enum Select {
    'YES',
    'NO',
    'UNDEFINED'
}

export class Bussiness implements BaseEntity {
    constructor(
        public id?: number,
        public bussinessType?: BussinessType,
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
    }
}
