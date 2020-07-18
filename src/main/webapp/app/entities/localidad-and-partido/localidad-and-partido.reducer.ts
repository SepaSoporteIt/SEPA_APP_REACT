import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILocalidadAndPartido, defaultValue } from 'app/shared/model/localidad-and-partido.model';

export const ACTION_TYPES = {
  FETCH_LOCALIDADANDPARTIDO_LIST: 'localidadAndPartido/FETCH_LOCALIDADANDPARTIDO_LIST',
  FETCH_LOCALIDADANDPARTIDO: 'localidadAndPartido/FETCH_LOCALIDADANDPARTIDO',
  CREATE_LOCALIDADANDPARTIDO: 'localidadAndPartido/CREATE_LOCALIDADANDPARTIDO',
  UPDATE_LOCALIDADANDPARTIDO: 'localidadAndPartido/UPDATE_LOCALIDADANDPARTIDO',
  DELETE_LOCALIDADANDPARTIDO: 'localidadAndPartido/DELETE_LOCALIDADANDPARTIDO',
  RESET: 'localidadAndPartido/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILocalidadAndPartido>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type LocalidadAndPartidoState = Readonly<typeof initialState>;

// Reducer

export default (state: LocalidadAndPartidoState = initialState, action): LocalidadAndPartidoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_LOCALIDADANDPARTIDO):
    case REQUEST(ACTION_TYPES.UPDATE_LOCALIDADANDPARTIDO):
    case REQUEST(ACTION_TYPES.DELETE_LOCALIDADANDPARTIDO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO):
    case FAILURE(ACTION_TYPES.CREATE_LOCALIDADANDPARTIDO):
    case FAILURE(ACTION_TYPES.UPDATE_LOCALIDADANDPARTIDO):
    case FAILURE(ACTION_TYPES.DELETE_LOCALIDADANDPARTIDO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_LOCALIDADANDPARTIDO):
    case SUCCESS(ACTION_TYPES.UPDATE_LOCALIDADANDPARTIDO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_LOCALIDADANDPARTIDO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/localidad-and-partidos';

// Actions

export const getEntities: ICrudGetAllAction<ILocalidadAndPartido> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO_LIST,
    payload: axios.get<ILocalidadAndPartido>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ILocalidadAndPartido> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO,
    payload: axios.get<ILocalidadAndPartido>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ILocalidadAndPartido> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LOCALIDADANDPARTIDO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILocalidadAndPartido> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LOCALIDADANDPARTIDO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILocalidadAndPartido> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LOCALIDADANDPARTIDO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
