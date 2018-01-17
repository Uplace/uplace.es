/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { UplaceTestModule } from '../../../test.module';
import { BussinessComponent } from '../../../../../../main/webapp/app/entities/bussiness/bussiness.component';
import { BussinessService } from '../../../../../../main/webapp/app/entities/bussiness/bussiness.service';
import { Bussiness } from '../../../../../../main/webapp/app/entities/bussiness/bussiness.model';

describe('Component Tests', () => {

    describe('Bussiness Management Component', () => {
        let comp: BussinessComponent;
        let fixture: ComponentFixture<BussinessComponent>;
        let service: BussinessService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [BussinessComponent],
                providers: [
                    BussinessService
                ]
            })
            .overrideTemplate(BussinessComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BussinessComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BussinessService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Bussiness(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bussinesses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
