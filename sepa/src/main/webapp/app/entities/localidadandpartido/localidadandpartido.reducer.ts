import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILocalidadandpartido, defaultValue } from 'app/shared/model/localidadandpartido.model';

export const ACTION_TYPES = {
  SEARCH_LOCALIDADANDPARTIDOS: 'localidadandpartido/SEARCH_LOCALIDADANDPARTIDOS',
  FETCH_LOCALIDADANDPARTIDO_LIST: 'localidadandpartido/FETCH_LOCALIDADANDPARTIDO_LIST',
  FETCH_LOCALIDADANDPARTIDO: 'localidadandpartido/FETCH_LOCALIDADANDPARTIDO',
  CREATE_LOCALIDADANDPARTIDO: 'localidadandpartido/CREATE_LOCALIDADANDPARTIDO',
  UPDATE_LOCALIDADANDPARTIDO: 'localidadandpartido/UPDATE_LOCALIDADANDPARTIDO',
  DELETE_LOCALIDADANDPARTIDO: 'localidadandpartido/DELETE_LOCALIDADANDPARTIDO',
  RESET: 'localidadandpartido/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILocalidadandpartido>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type LocalidadandpartidoState = Readonly<typeof initialState>;

// Reducer

export default (state: LocalidadandpartidoState = initialState, action): LocalidadandpartidoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_LOCALIDADANDPARTIDOS):
    case REQUEST(ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LOCALIDADANDPARTIDO):
    case REQUEST(ACTION_TYPES.UPDATE_LOCALIDADANDPARTIDO):
    case REQUEST(ACTION_TYPES.DELETE_LOCALIDADANDPARTIDO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_LOCALIDADANDPARTIDOS):
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
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_LOCALIDADANDPARTIDOS):
    case SUCCESS(ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LOCALIDADANDPARTIDO):
    case SUCCESS(ACTION_TYPES.UPDATE_LOCALIDADANDPARTIDO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LOCALIDADANDPARTIDO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/localidadandpartidos';
const apiSearchUrl = 'api/_search/localidadandpartidos';

// Actions

export const getSearchEntities: ICrudSearchAction<ILocalidadandpartido> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_LOCALIDADANDPARTIDOS,
  payload: axios.get<ILocalidadandpartido>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<ILocalidadandpartido> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO_LIST,
    payload: axios.get<ILocalidadandpartido>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ILocalidadandpartido> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LOCALIDADANDPARTIDO,
    payload: axios.get<ILocalidadandpartido>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILocalidadandpartido> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LOCALIDADANDPARTIDO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILocalidadandpartido> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LOCALIDADANDPARTIDO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILocalidadandpartido> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LOCALIDADANDPARTIDO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
