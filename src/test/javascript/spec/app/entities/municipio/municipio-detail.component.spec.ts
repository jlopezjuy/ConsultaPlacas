/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ConsultaPlacasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MunicipioDetailComponent } from '../../../../../../main/webapp/app/entities/municipio/municipio-detail.component';
import { MunicipioService } from '../../../../../../main/webapp/app/entities/municipio/municipio.service';
import { Municipio } from '../../../../../../main/webapp/app/entities/municipio/municipio.model';

describe('Component Tests', () => {

    describe('Municipio Management Detail Component', () => {
        let comp: MunicipioDetailComponent;
        let fixture: ComponentFixture<MunicipioDetailComponent>;
        let service: MunicipioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ConsultaPlacasTestModule],
                declarations: [MunicipioDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MunicipioService,
                    JhiEventManager
                ]
            }).overrideTemplate(MunicipioDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MunicipioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MunicipioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Municipio(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.municipio).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
