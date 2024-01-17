import { CREATE_ORDER_FAILURE, CREATE_ORDER_REQUEST, CREATE_ORDER_SUCCESS, GET_ORDER_BY_ID_FAILURE, GET_ORDER_BY_ID_SUCCESS, GET_ORDER_HISTORY_REQUEST, GET_ORDER_HISTORY_SUCCESS } from "./ActionType";


const initialState={
    order:null,
    error:null,
    loading:false
};

export const orderReducer = (state = initialState, action) => {
    switch(action.type){
        case CREATE_ORDER_REQUEST:
        case GET_ORDER_HISTORY_REQUEST:
            return {...state, loading:true}

        case CREATE_ORDER_SUCCESS:
        case GET_ORDER_BY_ID_SUCCESS:
            return {...state, loading:false, order:action.payload};

        case CREATE_ORDER_FAILURE:
        case GET_ORDER_BY_ID_FAILURE:
            return {...state, loading:false, error:action.payload};

        default:
            return state;
    }
}