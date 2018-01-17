import { BaseEntity } from './../../shared';

export const enum TerrainType {
    'RESIDENTIAL',
    'URBAN',
    'RUSTIC'
}

export const enum Select {
    'YES',
    'NO',
    'UNDEFINED'
}

export class Terrain implements BaseEntity {
    constructor(
        public id?: number,
        public terrainType?: TerrainType,
        public nearTransport?: Select,
    ) {
    }
}
