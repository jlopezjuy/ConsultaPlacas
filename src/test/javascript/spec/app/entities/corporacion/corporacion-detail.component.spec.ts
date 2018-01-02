/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ConsultaPlacasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CorporacionDetailComponent } from '../../../../../../main/webapp/app/entities/corporacion/corporacion-detail.component';
import { CorporacionService } from '../../../../../../main/webapp/app/entities/corporacion/corporacion.service';
import { Corporacion } from '../../../../../../main/webapp/app/entities/corporacion/corporacion.model';

describe('Component Tests', () => {

    describe('Corporacion Management Detail Component', () => {
        let comp: CorporacionDetailComponent;
        let fixture: ComponentFixture<CorporacionDetailComponent>;
        let service: CorporacionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ConsultaPlacasTestModule],
                declarations: [CorporacionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CorporacionService,
                    JhiEventManager
                ]
            }).overrideTemplate(CorporacionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CorporacionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CorporacionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Corporacion(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.corporacion).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
