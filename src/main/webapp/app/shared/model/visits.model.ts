import { Moment } from 'moment';
import { ICompany } from 'app/shared/model/company.model';

export interface IVisits {
  id?: number;
  employee?: string;
  visit_date?: string;
  comments?: string;
  company?: ICompany;
}

export const defaultValue: Readonly<IVisits> = {};
