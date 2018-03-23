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
            this.realEstates = [...this.realEstates];
            console.log(this.realEstates);
        });
    }

    onClear() {
        this.realEstate = new RealEstate();
    }

    onChange(event) {
        this.realEstateService.findProperties(this.realEstate.reference).subscribe((response) => {
            this.properties = response.body;
        })
    }

    onAdd(event) {
        console.log(event);
    }



}
