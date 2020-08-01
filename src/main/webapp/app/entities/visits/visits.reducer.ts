import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVisits, defaultValue } from 'app/shared/model/visits.model';

export const ACTION_TYPES = {
  FETCH_VISITS_LIST: 'visits/FETCH_VISITS_LIST',
  FETCH_VISITS: 'visits/FETCH_VISITS',
  CREATE_VISITS: 'visits/CREATE_VISITS',
  UPDATE_VISITS: 'visits/UPDATE_VISITS',
  DELETE_VISITS: 'visits/DELETE_VISITS',
  RESET: 'visits/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVisits>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type VisitsState = Readonly<typeof initialState>;

// Reducer

export default (state: VisitsState = initialState, action): VisitsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VISITS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VISITS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_VISITS):
    case REQUEST(ACTION_TYPES.UPDATE_VISITS):
    case REQUEST(ACTION_TYPES.DELETE_VISITS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_VISITS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VISITS):
    case FAILURE(ACTION_TYPES.CREATE_VISITS):
    case FAILURE(ACTION_TYPES.UPDATE_VISITS):
    case FAILURE(ACTION_TYPES.DELETE_VISITS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VISITS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_VISITS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_VISITS):
    case SUCCESS(ACTION_TYPES.UPDATE_VISITS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_VISITS):
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

const apiUrl = 'api/visits';

// Actions

export const getEntities: ICrudGetAllAction<IVisits> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_VISITS_LIST,
    payload: axios.get<IVisits>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IVisits> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VISITS,
    payload: axios.get<IVisits>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IVisits> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VISITS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVisits> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VISITS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVisits> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VISITS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
