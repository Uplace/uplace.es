import {Component, OnInit} from '@angular/core';
import {RealEstate, RealEstateService} from "../../../entities/real-estate";

@Component({
    selector: 'up-real-estate',
    templateUrl: './real-estate.component.html',
    styles: []
})
export class RealEstateComponent implements OnInit {

    realEstates: RealEstate[] = [];

    constructor(private realEstateService: RealEstateService) {
    }

    ngOnInit() {
        this.realEstateService.query().subscribe((result) => {
            this.realEstates = result.body;
        })
    }

}
