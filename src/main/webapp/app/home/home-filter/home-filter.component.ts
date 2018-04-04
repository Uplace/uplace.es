import {Component, OnInit} from '@angular/core';
import {HttpResponse} from "@angular/common/http";
import {FilterService} from "../../shared/filter/filter.service";
import {Filter} from "../../shared/filter/filter.model";

interface HomeFilter {
    keyword?: string;
    location?: string;
    category?: string;
    surface?: number;
    bedrooms?: number;
    bathrooms?: number;
    priceFrom?: number;
    priceTo?: number;
}

@Component({
    selector: 'up-home-filter',
    templateUrl: './home-filter.component.html',
    styles: []
})
export class HomeFilterComponent implements OnInit {

    filters: Filter = {};
    filter: HomeFilter = {};

    constructor(
        private filterService: FilterService
    ) {
    }

    loadAll() {
        this.filterService.query().subscribe(
            (res: HttpResponse<Filter>) => {
                this.filters = res.body;
            }
        );
    }

    ngOnInit() {
        this.loadAll();
    }

    onSubmit() {
        console.log(this.filter);
    }

}
