import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IIndustryType } from 'app/shared/model/industry-type.model';

export interface ICompany {
  id?: number;
  name?: string;
  email?: string;
  addressDirection?: string;
  addressNumber?: string;
  floor?: string;
  departament?: string;
  cuit?: string;
  isSubscripted?: boolean;
  comment?: any;
  fantasyName?: string;
  postalCode?: string;
  tlf?: string;
  internalTlf?: string;
  contact?: string;
  cellphone?: string;
  visitsQtyMin?: number;
  visitsQtyMax?: number;
  habPrim?: string;
  habSec?: string;
  legislationId?: number;
  solicitadorId?: number;
  ambitoId?: number;
  autoridadId?: number;
  frecuencyTypeId?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  employee?: IEmployee;
  primIndustryTipe?: IIndustryType;
  secIndustryTipe?: IIndustryType;
}

export const defaultValue: Readonly<ICompany> = {
  isSubscripted: false
};