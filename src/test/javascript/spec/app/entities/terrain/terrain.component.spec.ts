/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { UplaceTestModule } from '../../../test.module';
import { TerrainComponent } from '../../../../../../main/webapp/app/entities/terrain/terrain.component';
import { TerrainService } from '../../../../../../main/webapp/app/entities/terrain/terrain.service';
import { Terrain } from '../../../../../../main/webapp/app/entities/terrain/terrain.model';

describe('Component Tests', () => {

    describe('Terrain Management Component', () => {
        let comp: TerrainComponent;
        let fixture: ComponentFixture<TerrainComponent>;
        let service: TerrainService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [UplaceTestModule],
                declarations: [TerrainComponent],
                providers: [
                    TerrainService
                ]
            })
            .overrideTemplate(TerrainComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TerrainComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TerrainService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Terrain(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.terrains[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
