import {Property} from "../property";
import {Mail} from "../../shared/model/mail.model";

export class Contact {
    constructor (
        public mail?: Mail,
        public date?: any,
        public property?: Property,
    ) {

    }
}
