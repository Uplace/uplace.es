export class Mail {

    constructor(
        public to?: string,
        public subject?: string,
        public content?: string,
        public isMultipart?: boolean,
        public isHtml?: boolean
    ) {

    }
}
