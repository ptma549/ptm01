import { IJob } from 'app/shared/model/job.model';

export interface IClient {
  id?: number;
  name?: string;
  names?: IJob[];
}

export class Client implements IClient {
  constructor(public id?: number, public name?: string, public names?: IJob[]) {}
}
