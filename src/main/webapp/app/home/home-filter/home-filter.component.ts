import {Component, OnInit} from '@angular/core';
import {HttpResponse} from "@angular/common/http";
import {FilterService} from "../../shared/filter/filter.service";
import {Filter} from "../../shared/filter/filter.model";
import {SearchService} from "../../shared/search/search.service";
import {UserSearch} from "../../shared/search/search.model";
import {Router} from "@angular/router";

@Component({
    selector: 'up-home-filter',
    templateUrl: './home-filter.component.html',
    styles: []
})
export class HomeFilterComponent implements OnInit {

    filters: Filter = {};
    search: UserSearch = {};

    constructor(
        private filterService: FilterService,
        private searchService: SearchService,
        private router: Router
    ) {
    }

    loadAll() {
        this.filterService.find().subscribe(
            (res: HttpResponse<Filter>) => {
                this.filters = res.body;
                this.searchService.userSearch.subscribe(search => {
                    this.search = search;
                });
            }
        );
    }

    ngOnInit() {
        this.loadAll();
    }

    onSubmit() {
        this.searchService.changeUserSearch(this.search);
        console.log(this.search);
        this.router.navigate(['/properties']);
    }

}
