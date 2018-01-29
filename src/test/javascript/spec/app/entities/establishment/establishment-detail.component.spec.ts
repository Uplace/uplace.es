/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { EstablishmentDetailComponent } from '../../../../../../main/webapp/app/entities/establishment/establishment-detail.component';
import { EstablishmentService } from '../../../../../../main/webapp/app/entities/establishment/establishment.service';
import { Establishment } from '../../../../../../main/webapp/app/entities/establishment/establishment.model';

describe('Component Tests', () => {

    describe('Establishment Management Detail Component', () => {
        let comp: EstablishmentDetailComponent;
        let fixture: ComponentFixture<EstablishmentDetailComponent>;
        let service: EstablishmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [EstablishmentDetailComponent],
                providers: [
                    EstablishmentService
                ]
            })
            .overrideTemplate(EstablishmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EstablishmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstablishmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Establishment(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.establishment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
