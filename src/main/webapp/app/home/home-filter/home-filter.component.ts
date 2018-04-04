import {Component, OnInit} from '@angular/core';
import {Filter} from "../../entities/filter/filter.model";
import {HttpResponse} from "@angular/common/http";
import {FilterService} from "../../entities/filter/filter.service";

@Component({
    selector: 'up-home-filter',
    templateUrl: './home-filter.component.html',
    styles: []
})
export class HomeFilterComponent implements OnInit {

    filters: Filter = {};


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

}
