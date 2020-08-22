import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IExpiration, defaultValue } from 'app/shared/model/expiration.model';

export const ACTION_TYPES = {
  SEARCH_EXPIRATIONS: 'expiration/SEARCH_EXPIRATIONS',
  FETCH_EXPIRATION_LIST: 'expiration/FETCH_EXPIRATION_LIST',
  FETCH_EXPIRATION: 'expiration/FETCH_EXPIRATION',
  CREATE_EXPIRATION: 'expiration/CREATE_EXPIRATION',
  UPDATE_EXPIRATION: 'expiration/UPDATE_EXPIRATION',
  DELETE_EXPIRATION: 'expiration/DELETE_EXPIRATION',
  SET_BLOB: 'expiration/SET_BLOB',
  RESET: 'expiration/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IExpiration>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ExpirationState = Readonly<typeof initialState>;

// Reducer

export default (state: ExpirationState = initialState, action): ExpirationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_EXPIRATIONS):
    case REQUEST(ACTION_TYPES.FETCH_EXPIRATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EXPIRATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EXPIRATION):
    case REQUEST(ACTION_TYPES.UPDATE_EXPIRATION):
    case REQUEST(ACTION_TYPES.DELETE_EXPIRATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_EXPIRATIONS):
    case FAILURE(ACTION_TYPES.FETCH_EXPIRATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EXPIRATION):
    case FAILURE(ACTION_TYPES.CREATE_EXPIRATION):
    case FAILURE(ACTION_TYPES.UPDATE_EXPIRATION):
    case FAILURE(ACTION_TYPES.DELETE_EXPIRATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_EXPIRATIONS):
    case SUCCESS(ACTION_TYPES.FETCH_EXPIRATION_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_EXPIRATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EXPIRATION):
    case SUCCESS(ACTION_TYPES.UPDATE_EXPIRATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EXPIRATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/expirations';
const apiSearchUrl = 'api/_search/expirations';

// Actions

export const getSearchEntities: ICrudSearchAction<IExpiration> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_EXPIRATIONS,
  payload: axios.get<IExpiration>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IExpiration> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EXPIRATION_LIST,
    payload: axios.get<IExpiration>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IExpiration> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EXPIRATION,
    payload: axios.get<IExpiration>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IExpiration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EXPIRATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IExpiration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EXPIRATION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IExpiration> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EXPIRATION,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
