/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UplaceTestModule } from '../../../test.module';
import { EstablishmentComponent } from '../../../../../../main/webapp/app/entities/establishment/establishment.component';
import { EstablishmentService } from '../../../../../../main/webapp/app/entities/establishment/establishment.service';
import { Establishment } from '../../../../../../main/webapp/app/entities/establishment/establishment.model';

describe('Component Tests', () => {

    describe('Establishment Management Component', () => {
        let comp: EstablishmentComponent;
        let fixture: ComponentFixture<EstablishmentComponent>;
        let service: EstablishmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [EstablishmentComponent],
                providers: [
                    EstablishmentService
                ]
            })
            .overrideTemplate(EstablishmentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EstablishmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstablishmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Establishment(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.establishments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
