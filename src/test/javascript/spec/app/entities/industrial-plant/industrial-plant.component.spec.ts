/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { UplaceTestModule } from '../../../test.module';
import { IndustrialPlantComponent } from '../../../../../../main/webapp/app/entities/industrial-plant/industrial-plant.component';
import { IndustrialPlantService } from '../../../../../../main/webapp/app/entities/industrial-plant/industrial-plant.service';
import { IndustrialPlant } from '../../../../../../main/webapp/app/entities/industrial-plant/industrial-plant.model';

describe('Component Tests', () => {

    describe('IndustrialPlant Management Component', () => {
        let comp: IndustrialPlantComponent;
        let fixture: ComponentFixture<IndustrialPlantComponent>;
        let service: IndustrialPlantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [IndustrialPlantComponent],
                providers: [
                    IndustrialPlantService
                ]
            })
            .overrideTemplate(IndustrialPlantComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustrialPlantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustrialPlantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new IndustrialPlant(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.industrialPlants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
