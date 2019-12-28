import { Moment } from 'moment';
import { ICompany } from 'app/shared/model/company.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IStudy } from 'app/shared/model/study.model';

export interface IExpiration {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  status?: string;
  comments?: any;
  company?: ICompany;
  employee?: IEmployee;
  study?: IStudy;
}

export const defaultValue: Readonly<IExpiration> = {};
