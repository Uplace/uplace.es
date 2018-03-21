import {Component, EventEmitter, OnInit} from '@angular/core';
import {RealEstate, RealEstateService} from "../../../entities/real-estate";
import {Property} from "../../../entities/property";

@Component({
    selector: 'up-real-estate',
    templateUrl: './real-estate.component.html',
    styles: []
})
export class RealEstateComponent implements OnInit {

    realEstates: RealEstate[] = [];

    realEstatesFiltered: RealEstate[] = [];

    searchTerm = new EventEmitter<string>();

    realEstate: RealEstate;

    properties: Property[] = [];

    constructor(private realEstateService: RealEstateService) { }

    ngOnInit() {
        this.realEstateService.query().subscribe((result) => {
            this.realEstates = result.body;
        })
    }

    onClear() {
        this.realEstate = new RealEstate();
    }

    onChange(event) {
        console.log(event);
        console.log(this.realEstate);
        this.realEstateService.findProperties(this.realEstate.reference).subscribe((response) => {
            this.properties = response.body;
        })
    }



}
