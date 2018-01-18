/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { BussinessDetailComponent } from '../../../../../../main/webapp/app/entities/bussiness/bussiness-detail.component';
import { BussinessService } from '../../../../../../main/webapp/app/entities/bussiness/bussiness.service';
import { Bussiness } from '../../../../../../main/webapp/app/entities/bussiness/bussiness.model';

describe('Component Tests', () => {

    describe('Bussiness Management Detail Component', () => {
        let comp: BussinessDetailComponent;
        let fixture: ComponentFixture<BussinessDetailComponent>;
        let service: BussinessService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [BussinessDetailComponent],
                providers: [
                    BussinessService
                ]
            })
            .overrideTemplate(BussinessDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BussinessDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BussinessService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Bussiness(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bussiness).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
