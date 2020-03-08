import { Moment } from 'moment';
import { ILocalidadandpartido } from 'app/shared/model/localidadandpartido.model';

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
  createdAt?: Moment;
  updatedAt?: Moment;
  localidadId?: ILocalidadandpartido;
  partidoId?: ILocalidadandpartido;
}

export const defaultValue: Readonly<IEmployee> = {
  isInternal: false
};
