
export class FilterModel {
    constructor(
        public cities?: string[],
        public pricesRange?: number[],
        public maxPrice?: number,
        public minPrice?: number,
        public typeProperties?: string[]
    ) {
    }
}
