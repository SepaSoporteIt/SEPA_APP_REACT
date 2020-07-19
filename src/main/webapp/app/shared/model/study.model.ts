export interface IStudy {
  id?: number;
  name?: string;
  resolution?: string;
  legislation?: string;
}

export const defaultValue: Readonly<IStudy> = {};
