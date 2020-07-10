import { Moment } from 'moment';

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
  localidad?: string;
  partido?: string;
  especializacion?: string;
  celular?: string;
  comentario?: string;
  createdAt?: string;
  updatedAt?: string;
}

export const defaultValue: Readonly<IEmployee> = {
  isInternal: false,
};
