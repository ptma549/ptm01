import { Moment } from 'moment';
import { IClient } from 'app/shared/model/client.model';

export interface IJob {
  id?: number;
  jobId?: string;
  completed?: Moment;
  reportedBy?: string;
  reportedOn?: Moment;
  clientOrderId?: string;
  priority?: number;
  fault?: string;
  accessInstructions?: string;
  address?: string;
  occupiersName?: string;
  occupiersHomePhone?: string;
  occupiersWorkPhone?: string;
  occupiersMobilePhone?: string;
  occupiersTenantSignature?: string;
  clients?: IClient[];
}

export class Job implements IJob {
  constructor(
    public id?: number,
    public jobId?: string,
    public completed?: Moment,
    public reportedBy?: string,
    public reportedOn?: Moment,
    public clientOrderId?: string,
    public priority?: number,
    public fault?: string,
    public accessInstructions?: string,
    public address?: string,
    public occupiersName?: string,
    public occupiersHomePhone?: string,
    public occupiersWorkPhone?: string,
    public occupiersMobilePhone?: string,
    public occupiersTenantSignature?: string,
    public clients?: IClient[]
  ) {}
}
