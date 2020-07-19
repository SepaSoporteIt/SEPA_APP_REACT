import { Moment } from 'moment';
import { ICompany } from 'app/shared/model/company.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IStudy } from 'app/shared/model/study.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IExpiration {
  id?: number;
  startDate?: string;
  endDate?: string;
  status?: Status;
  comments?: string;
  uniqueCode?: string;
  responsible?: string;
  isCompleted?: boolean;
  company?: ICompany;
  employee?: IEmployee;
  study?: IStudy;
}

export const defaultValue: Readonly<IExpiration> = {
  isCompleted: false,
};
