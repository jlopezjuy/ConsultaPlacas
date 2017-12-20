/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ConsultaPlacasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RadioDetailComponent } from '../../../../../../main/webapp/app/entities/radio/radio-detail.component';
import { RadioService } from '../../../../../../main/webapp/app/entities/radio/radio.service';
import { Radio } from '../../../../../../main/webapp/app/entities/radio/radio.model';

describe('Component Tests', () => {

    describe('Radio Management Detail Component', () => {
        let comp: RadioDetailComponent;
        let fixture: ComponentFixture<RadioDetailComponent>;
        let service: RadioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ConsultaPlacasTestModule],
                declarations: [RadioDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RadioService,
                    JhiEventManager
                ]
            }).overrideTemplate(RadioDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RadioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RadioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Radio(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.radio).toEqual(jasmine.objectContaining({issi: 10}));
            });
        });
    });

});
