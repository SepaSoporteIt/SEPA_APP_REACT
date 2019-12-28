import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IIndustryType, defaultValue } from 'app/shared/model/industry-type.model';

export const ACTION_TYPES = {
  SEARCH_INDUSTRYTYPES: 'industryType/SEARCH_INDUSTRYTYPES',
  FETCH_INDUSTRYTYPE_LIST: 'industryType/FETCH_INDUSTRYTYPE_LIST',
  FETCH_INDUSTRYTYPE: 'industryType/FETCH_INDUSTRYTYPE',
  CREATE_INDUSTRYTYPE: 'industryType/CREATE_INDUSTRYTYPE',
  UPDATE_INDUSTRYTYPE: 'industryType/UPDATE_INDUSTRYTYPE',
  DELETE_INDUSTRYTYPE: 'industryType/DELETE_INDUSTRYTYPE',
  RESET: 'industryType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IIndustryType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type IndustryTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: IndustryTypeState = initialState, action): IndustryTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_INDUSTRYTYPES):
    case REQUEST(ACTION_TYPES.FETCH_INDUSTRYTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INDUSTRYTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_INDUSTRYTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_INDUSTRYTYPE):
    case REQUEST(ACTION_TYPES.DELETE_INDUSTRYTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_INDUSTRYTYPES):
    case FAILURE(ACTION_TYPES.FETCH_INDUSTRYTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INDUSTRYTYPE):
    case FAILURE(ACTION_TYPES.CREATE_INDUSTRYTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_INDUSTRYTYPE):
    case FAILURE(ACTION_TYPES.DELETE_INDUSTRYTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_INDUSTRYTYPES):
    case SUCCESS(ACTION_TYPES.FETCH_INDUSTRYTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_INDUSTRYTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_INDUSTRYTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_INDUSTRYTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_INDUSTRYTYPE):
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

const apiUrl = 'api/industry-types';
const apiSearchUrl = 'api/_search/industry-types';

// Actions

export const getSearchEntities: ICrudSearchAction<IIndustryType> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_INDUSTRYTYPES,
  payload: axios.get<IIndustryType>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IIndustryType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_INDUSTRYTYPE_LIST,
    payload: axios.get<IIndustryType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IIndustryType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INDUSTRYTYPE,
    payload: axios.get<IIndustryType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IIndustryType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INDUSTRYTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IIndustryType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INDUSTRYTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IIndustryType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INDUSTRYTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
