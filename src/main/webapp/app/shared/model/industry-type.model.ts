import { Moment } from 'moment';

export interface IIndustryType {
  id?: number;
  name?: string;
  ciiu?: string;
  createdAt?: string;
  updatedAt?: string;
}

export const defaultValue: Readonly<IIndustryType> = {};
