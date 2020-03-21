import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IIndustryType } from 'app/shared/model/industry-type.model';
import { ILocalidadandpartido } from 'app/shared/model/localidadandpartido.model';

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
  comment?: any;
  createdAt?: Moment;
  updatedAt?: Moment;
  betweenStreets?: string;
  isDisabled?: boolean;
  employee?: IEmployee;
  primIndustryTipe?: IIndustryType;
  secIndustryTipe?: IIndustryType;
  localidadId?: ILocalidadandpartido;
  partidoId?: ILocalidadandpartido;
}

export const defaultValue: Readonly<ICompany> = {
  isSubscripted: false,
  isDisabled: false
};
