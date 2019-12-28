import { Moment } from 'moment';

export interface IEmployee {
  id?: number;
  name?: string;
  surname?: string;
  email?: string;
  tlf?: string;
  tipo?: boolean;
  matNumber?: string;
  cuit?: string;
  addressDirection?: string;
  addressNumber?: string;
  floor?: string;
  departament?: string;
  degree?: string;
  localidad?: string;
  partido?: string;
  especializacion?: string;
  celular?: string;
  comentario?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
}

export const defaultValue: Readonly<IEmployee> = {
  tipo: false
};
