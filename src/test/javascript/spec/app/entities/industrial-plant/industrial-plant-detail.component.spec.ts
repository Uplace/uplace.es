/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { IndustrialPlantDetailComponent } from '../../../../../../main/webapp/app/entities/industrial-plant/industrial-plant-detail.component';
import { IndustrialPlantService } from '../../../../../../main/webapp/app/entities/industrial-plant/industrial-plant.service';
import { IndustrialPlant } from '../../../../../../main/webapp/app/entities/industrial-plant/industrial-plant.model';

describe('Component Tests', () => {

    describe('IndustrialPlant Management Detail Component', () => {
        let comp: IndustrialPlantDetailComponent;
        let fixture: ComponentFixture<IndustrialPlantDetailComponent>;
        let service: IndustrialPlantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [IndustrialPlantDetailComponent],
                providers: [
                    IndustrialPlantService
                ]
            })
            .overrideTemplate(IndustrialPlantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustrialPlantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustrialPlantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new IndustrialPlant(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.industrialPlant).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
