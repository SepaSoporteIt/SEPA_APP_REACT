import { Moment } from 'moment';

export interface IIndustryType {
  id?: number;
  name?: string;
  ciiu?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
}

export const defaultValue: Readonly<IIndustryType> = {};
