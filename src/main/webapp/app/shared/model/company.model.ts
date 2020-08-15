import { IEmployee } from 'app/shared/model/employee.model';
import { IIndustryType } from 'app/shared/model/industry-type.model';
import { ILocalidadAndPartido } from 'app/shared/model/localidad-and-partido.model';

export interface ICompany {
  id?: number;
  name?: string;
  email?: string;
  addressDirection?: string;
  addressNumber?: string;
  betweenStreets?: string;
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
  comment?: string;
  isDisabled?: boolean;
  employee?: IEmployee;
  industryType?: IIndustryType;
  secIndustryType?: IIndustryType;
  localidadAndPartido?: ILocalidadAndPartido;
}

export const defaultValue: Readonly<ICompany> = {
  isSubscripted: false,
  isDisabled: false,
};
