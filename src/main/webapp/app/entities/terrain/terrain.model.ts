import { BaseEntity } from './../../shared';

export const enum TerrainType {
    'RESIDENTIAL',
    'URBAN',
    'RUSTIC',
    'INDUSTRIAL'
}

export class Terrain implements BaseEntity {
    constructor(
        public id?: number,
        public m2Buildable?: number,
        public buildableDepth?: number,
        public floorsSR?: number,
        public floorsBR?: number,
        public constructionCoefficient?: number,
        public type?: TerrainType,
    ) {
    }
}
