import {AfterContentInit, AfterViewChecked, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {RealEstate, RealEstateService} from "../../../../entities/real-estate";
import {Observable} from "rxjs/Observable";
import {Property} from "../../../../entities/property/property.model";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {EventManager} from "@angular/platform-browser";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";
import {el} from "@angular/platform-browser/testing/src/browser_util";

@Component({
    selector: 'up-property-estate',
    templateUrl: './property-estate.component.html',
    styles: []
})
export class PropertyEstateComponent implements OnInit, AfterViewChecked {

    @Input() realEstate: RealEstate = new RealEstate();

    @Output() realEstateChange: EventEmitter<RealEstate> = new EventEmitter<RealEstate>();

    realEstatesFiltered: RealEstate[] = [];

    realEstates: RealEstate[] = [];

    constructor(private realEstateService: RealEstateService,
                private eventManager: JhiEventManager,
                private jhiAlertService: JhiAlertService) {
    }

    ngOnInit() {
        this.loadAll();
    }

    ngAfterViewChecked() {
        if (this.realEstate == null) {
            this.realEstate = new RealEstate();
        }
    }

    loadAll() {
        this.realEstateService.query().subscribe((result) => {
            this.realEstates = result.body;
        })
    }

    onChange(event) {
        this.realEstateChange.emit(this.realEstate);
    }

    onClear() {
        this.realEstate = new RealEstate();
    }

    saveRealEstate() {
        if (this.realEstate.id == null) {
            this.subscribeToSaveResponse(
                this.realEstateService.create(this.realEstate));
        } else {
            this.subscribeToSaveResponse(
                this.realEstateService.update(this.realEstate));
        }

    }

    deleteRealEstate(realEstate: RealEstate) {
        this.realEstateService.delete(realEstate.id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'realEstateListModification',
                content: 'Deleted an property'
            });
            this.loadAll();
        });
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RealEstate>>) {
        result.subscribe((res: HttpResponse<RealEstate>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onError(res.error));
    }

    private onSaveSuccess(result: RealEstate) {
        this.realEstates.push(result);
        this.realEstate = result;
        this.eventManager.broadcast({name: 'propertyListModification', content: 'OK'});
        this.realEstateChange.emit(this.realEstate);
        this.loadAll();
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

}
