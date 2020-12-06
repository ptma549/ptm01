import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JobService } from 'app/entities/job/job.service';
import { IJob, Job } from 'app/shared/model/job.model';

describe('Service Tests', () => {
  describe('Job Service', () => {
    let injector: TestBed;
    let service: JobService;
    let httpMock: HttpTestingController;
    let elemDefault: IJob;
    let expectedResult: IJob | IJob[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(JobService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Job(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            completed: currentDate.format(DATE_TIME_FORMAT),
            reportedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Job', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            completed: currentDate.format(DATE_TIME_FORMAT),
            reportedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            completed: currentDate,
            reportedOn: currentDate,
          },
          returnedFromService
        );

        service.create(new Job()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Job', () => {
        const returnedFromService = Object.assign(
          {
            jobId: 'BBBBBB',
            completed: currentDate.format(DATE_TIME_FORMAT),
            reportedBy: 'BBBBBB',
            reportedOn: currentDate.format(DATE_TIME_FORMAT),
            clientOrderId: 'BBBBBB',
            priority: 1,
            fault: 'BBBBBB',
            accessInstructions: 'BBBBBB',
            address: 'BBBBBB',
            occupiersName: 'BBBBBB',
            occupiersHomePhone: 'BBBBBB',
            occupiersWorkPhone: 'BBBBBB',
            occupiersMobilePhone: 'BBBBBB',
            occupiersTenantSignature: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            completed: currentDate,
            reportedOn: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Job', () => {
        const returnedFromService = Object.assign(
          {
            jobId: 'BBBBBB',
            completed: currentDate.format(DATE_TIME_FORMAT),
            reportedBy: 'BBBBBB',
            reportedOn: currentDate.format(DATE_TIME_FORMAT),
            clientOrderId: 'BBBBBB',
            priority: 1,
            fault: 'BBBBBB',
            accessInstructions: 'BBBBBB',
            address: 'BBBBBB',
            occupiersName: 'BBBBBB',
            occupiersHomePhone: 'BBBBBB',
            occupiersWorkPhone: 'BBBBBB',
            occupiersMobilePhone: 'BBBBBB',
            occupiersTenantSignature: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            completed: currentDate,
            reportedOn: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Job', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
