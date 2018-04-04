import {Component, OnInit} from '@angular/core';
import {SearchService} from "../../../shared/search/search.service";
import {UserSearch} from "../../../shared/search/search.model";

@Component({
    selector: 'up-property-filter',
    templateUrl: './property-filter.component.html',
    styles: []
})
export class PropertyFilterComponent implements OnInit {

    searchUserData: UserSearch;

    constructor(
        private searchService: SearchService
    ) {
    }

    ngOnInit() {
        this.searchUserData = this.searchService.getSearch();
        console.log('property-filter');
        console.log(this.searchService);
    }

}
