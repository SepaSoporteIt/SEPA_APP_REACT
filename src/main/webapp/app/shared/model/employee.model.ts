import { ICompany } from 'app/shared/model/company.model';
import { ILocalidadAndPartido } from 'app/shared/model/localidad-and-partido.model';

export interface IEmployee {
  id?: number;
  name?: string;
  surname?: string;
  email?: string;
  tlf?: string;
  isInternal?: boolean;
  matNumber?: string;
  cuit?: string;
  addressDirection?: string;
  addressNumber?: string;
  floor?: string;
  departament?: string;
  degree?: string;
  especializacion?: string;
  celular?: string;
  comentario?: string;
  isDisabled?: boolean;
  company?: ICompany;
  localidadId?: ILocalidadAndPartido;
  partidoId?: ILocalidadAndPartido;
}

export const defaultValue: Readonly<IEmployee> = {
  isInternal: false,
  isDisabled: false,
};
