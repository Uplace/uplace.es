import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'up-property-feature',
    templateUrl: './property-feature.component.html',
    styles: []
})

export class PropertyFeatureComponent implements OnInit {

    @Input() fieldValue: any;
    @Input() fieldName: string;
    @Input() fieldType: | 'select' | 'energycertificate';

    constructor() {
    }

    ngOnInit() {

    }

}
