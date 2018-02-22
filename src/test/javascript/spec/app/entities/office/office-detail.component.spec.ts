/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { OfficeDetailComponent } from '../../../../../../main/webapp/app/entities/office/office-detail.component';
import { OfficeService } from '../../../../../../main/webapp/app/entities/office/office.service';
import { Office } from '../../../../../../main/webapp/app/entities/office/office.model';

describe('Component Tests', () => {

    describe('Office Management Detail Component', () => {
        let comp: OfficeDetailComponent;
        let fixture: ComponentFixture<OfficeDetailComponent>;
        let service: OfficeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [OfficeDetailComponent],
                providers: [
                    OfficeService
                ]
            })
            .overrideTemplate(OfficeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OfficeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OfficeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Office(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.office).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
