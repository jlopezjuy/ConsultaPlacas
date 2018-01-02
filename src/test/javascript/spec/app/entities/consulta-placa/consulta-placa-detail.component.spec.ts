/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ConsultaPlacasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConsultaPlacaDetailComponent } from '../../../../../../main/webapp/app/entities/consulta-placa/consulta-placa-detail.component';
import { ConsultaPlacaService } from '../../../../../../main/webapp/app/entities/consulta-placa/consulta-placa.service';
import { ConsultaPlaca } from '../../../../../../main/webapp/app/entities/consulta-placa/consulta-placa.model';

describe('Component Tests', () => {

    describe('ConsultaPlaca Management Detail Component', () => {
        let comp: ConsultaPlacaDetailComponent;
        let fixture: ComponentFixture<ConsultaPlacaDetailComponent>;
        let service: ConsultaPlacaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ConsultaPlacasTestModule],
                declarations: [ConsultaPlacaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConsultaPlacaService,
                    JhiEventManager
                ]
            }).overrideTemplate(ConsultaPlacaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConsultaPlacaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConsultaPlacaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ConsultaPlaca(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.consultaPlaca).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
