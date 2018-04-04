import {Component, OnInit} from '@angular/core';
import {SearchService} from "../../../shared/search/search.service";
import {UserSearch} from "../../../shared/search/search.model";
import {FilterService} from "../../../shared/filter/filter.service";
import {Filter} from "../../../shared/filter/filter.model";
import {HttpResponse} from "@angular/common/http";

@Component({
    selector: 'up-property-filter',
    templateUrl: './property-filter.component.html',
    styles: []
})
export class PropertyFilterComponent implements OnInit {

    filters: Filter = {};
    searchUserData: UserSearch = {};

    constructor(
        private searchService: SearchService,
        private filterService: FilterService
    ) {
    }

    ngOnInit() {
        this.filterService.find().subscribe((res: HttpResponse<Filter>) => {
           this.filters = res.body;
           this.searchUserData = this.searchService.getSearch();
        });
    }

    onSubmit() {
        this.searchService.setSearch(this.searchUserData);

    }

}
