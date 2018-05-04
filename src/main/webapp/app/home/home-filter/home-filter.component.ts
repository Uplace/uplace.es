import {Component, OnInit} from '@angular/core';
import {HttpResponse} from "@angular/common/http";
import {FilterService} from "../../shared/filter/filter.service";
import {Filter} from "../../shared/filter/filter.model";
import {SearchService} from "../../shared/search/search.service";
import {UserCriteria} from "../../shared/search/user-criteria.model";
import {Router} from "@angular/router";

@Component({
    selector: 'up-home-filter',
    templateUrl: './home-filter.component.html',
    styles: []
})
export class HomeFilterComponent implements OnInit {

    filters: Filter = {};
    criteria: UserCriteria = {};

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
                this.searchService.userCriteria.subscribe(criteria => {
                    this.criteria = criteria;
                });
            }
        );
    }

    ngOnInit() {
        this.loadAll();
    }

    onSubmit() {
        this.searchService.changeUserCriteria(this.criteria);
        console.log(this.criteria);
        this.router.navigate(['/properties']);
    }

}
