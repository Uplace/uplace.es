import {Property} from "../property";

export class Contact {
    constructor (
        public to?: string,
        public name?: string,
        public phone?: string,
        public date?: any,
        public property?: Property,
    ) {

    }
}
