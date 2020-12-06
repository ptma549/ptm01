import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IJob, Job } from 'app/shared/model/job.model';
import { JobService } from './job.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';

@Component({
  selector: 'jhi-job-update',
  templateUrl: './job-update.component.html',
})
export class JobUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClient[] = [];

  editForm = this.fb.group({
    id: [],
    jobId: [],
    completed: [],
    reportedBy: [],
    reportedOn: [],
    clientOrderId: [],
    priority: [],
    fault: [],
    accessInstructions: [],
    address: [],
    occupiersName: [],
    occupiersHomePhone: [],
    occupiersWorkPhone: [],
    occupiersMobilePhone: [],
    occupiersTenantSignature: [],
    clients: [],
  });

  constructor(
    protected jobService: JobService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ job }) => {
      if (!job.id) {
        const today = moment().startOf('day');
        job.completed = today;
        job.reportedOn = today;
      }

      this.updateForm(job);

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));
    });
  }

  updateForm(job: IJob): void {
    this.editForm.patchValue({
      id: job.id,
      jobId: job.jobId,
      completed: job.completed ? job.completed.format(DATE_TIME_FORMAT) : null,
      reportedBy: job.reportedBy,
      reportedOn: job.reportedOn ? job.reportedOn.format(DATE_TIME_FORMAT) : null,
      clientOrderId: job.clientOrderId,
      priority: job.priority,
      fault: job.fault,
      accessInstructions: job.accessInstructions,
      address: job.address,
      occupiersName: job.occupiersName,
      occupiersHomePhone: job.occupiersHomePhone,
      occupiersWorkPhone: job.occupiersWorkPhone,
      occupiersMobilePhone: job.occupiersMobilePhone,
      occupiersTenantSignature: job.occupiersTenantSignature,
      clients: job.clients,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const job = this.createFromForm();
    if (job.id !== undefined) {
      this.subscribeToSaveResponse(this.jobService.update(job));
    } else {
      this.subscribeToSaveResponse(this.jobService.create(job));
    }
  }

  private createFromForm(): IJob {
    return {
      ...new Job(),
      id: this.editForm.get(['id'])!.value,
      jobId: this.editForm.get(['jobId'])!.value,
      completed: this.editForm.get(['completed'])!.value ? moment(this.editForm.get(['completed'])!.value, DATE_TIME_FORMAT) : undefined,
      reportedBy: this.editForm.get(['reportedBy'])!.value,
      reportedOn: this.editForm.get(['reportedOn'])!.value ? moment(this.editForm.get(['reportedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      clientOrderId: this.editForm.get(['clientOrderId'])!.value,
      priority: this.editForm.get(['priority'])!.value,
      fault: this.editForm.get(['fault'])!.value,
      accessInstructions: this.editForm.get(['accessInstructions'])!.value,
      address: this.editForm.get(['address'])!.value,
      occupiersName: this.editForm.get(['occupiersName'])!.value,
      occupiersHomePhone: this.editForm.get(['occupiersHomePhone'])!.value,
      occupiersWorkPhone: this.editForm.get(['occupiersWorkPhone'])!.value,
      occupiersMobilePhone: this.editForm.get(['occupiersMobilePhone'])!.value,
      occupiersTenantSignature: this.editForm.get(['occupiersTenantSignature'])!.value,
      clients: this.editForm.get(['clients'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJob>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IClient): any {
    return item.id;
  }

  getSelected(selectedVals: IClient[], option: IClient): IClient {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
