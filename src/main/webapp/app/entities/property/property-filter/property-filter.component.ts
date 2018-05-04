import {Component, OnInit, AfterViewInit, OnChanges, SimpleChanges} from '@angular/core';
import {SearchService} from "../../../shared/search/search.service";
import {UserCriteria} from "../../../shared/search/user-criteria.model";
import {FilterService} from "../../../shared/filter/filter.service";
import {Filter} from "../../../shared/filter/filter.model";
import {HttpResponse} from "@angular/common/http";
import {ViewChild} from "@angular/core";
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/map';

@Component({
    selector: 'up-property-filter',
    templateUrl: './property-filter.component.html',
    styles: []
})
export class PropertyFilterComponent implements OnInit {


    @ViewChild('filterForm') filterForm;
    filters: Filter = {};
    criteria: UserCriteria = {};

    constructor(
        private searchService: SearchService,
        private filterService: FilterService
    ) {
    }


    ngOnInit() {
        this.filterService.find().subscribe((res: HttpResponse<Filter>) => {
            this.filters = res.body;
            this.filterForm.controls.keywords.valueChanges.debounceTime(700).subscribe(() => this.onSubmit());
            this.filterForm.controls.priceFrom.valueChanges.debounceTime(700).subscribe(() => this.onSubmit());
            this.filterForm.controls.priceTo.valueChanges.debounceTime(700).subscribe(() => this.onSubmit());
            this.searchService.userCriteria.subscribe((criteria: UserCriteria) => {
                this.criteria = criteria;
            });
        });
    }

    onSubmit() {
        this.searchService.changeUserCriteria(this.criteria);
    }

}
