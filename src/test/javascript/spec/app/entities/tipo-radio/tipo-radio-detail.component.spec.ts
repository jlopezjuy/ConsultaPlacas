/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ConsultaPlacasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TipoRadioDetailComponent } from '../../../../../../main/webapp/app/entities/tipo-radio/tipo-radio-detail.component';
import { TipoRadioService } from '../../../../../../main/webapp/app/entities/tipo-radio/tipo-radio.service';
import { TipoRadio } from '../../../../../../main/webapp/app/entities/tipo-radio/tipo-radio.model';

describe('Component Tests', () => {

    describe('TipoRadio Management Detail Component', () => {
        let comp: TipoRadioDetailComponent;
        let fixture: ComponentFixture<TipoRadioDetailComponent>;
        let service: TipoRadioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ConsultaPlacasTestModule],
                declarations: [TipoRadioDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TipoRadioService,
                    JhiEventManager
                ]
            }).overrideTemplate(TipoRadioDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoRadioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoRadioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TipoRadio(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tipoRadio).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
