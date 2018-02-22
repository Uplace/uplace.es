/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { UplaceTestModule } from '../../../test.module';
import { TerrainDetailComponent } from '../../../../../../main/webapp/app/entities/terrain/terrain-detail.component';
import { TerrainService } from '../../../../../../main/webapp/app/entities/terrain/terrain.service';
import { Terrain } from '../../../../../../main/webapp/app/entities/terrain/terrain.model';

describe('Component Tests', () => {

    describe('Terrain Management Detail Component', () => {
        let comp: TerrainDetailComponent;
        let fixture: ComponentFixture<TerrainDetailComponent>;
        let service: TerrainService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [TerrainDetailComponent],
                providers: [
                    TerrainService
                ]
            })
            .overrideTemplate(TerrainDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TerrainDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TerrainService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Terrain(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.terrain).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
