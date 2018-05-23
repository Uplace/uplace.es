import {Property} from "../../entities/property/index";

export const enum TerrainType {
    'RESIDENTIAL',
    'URBAN',
    'RUSTIC',
    'INDUSTRIAL'
}

export class Terrain extends Property {
    constructor(
        public m2Buildable?: number,
        public buildable?: boolean,
        public buildableDepth?: number,
        public floorsSR?: number,
        public floorsBR?: number,
        public constructionCoefficient?: number,
        public type?: TerrainType,
    ) {
        super();
        this.buildable = false;
        this.m2Buildable = 0;
        this.buildableDepth = 0;
        this.floorsBR = 0;
        this.floorsSR = 0;
        this.constructionCoefficient = 0;
    }
}
