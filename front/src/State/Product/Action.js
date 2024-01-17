import axios from "axios";
import { API_BASE_URL, api, apiGet } from "../../config/apiConfig";
import {
    FIND_PPRODUCT_BY_ID_FAILURE,
  FIND_PPRODUCT_BY_ID_REQUEST,
  FIND_PPRODUCT_BY_ID_SUCCESS,
  FIND_PRODUCTS_FAILURE,
  FIND_PRODUCTS_REQUEST,
  FIND_PRODUCTS_SUCCESS,
} from "./ActionType";

export const findProducts = (reqData) => async (dispatch) => {
  dispatch({ type: FIND_PRODUCTS_REQUEST });


  var defaultSearchParam = {
    color: reqData.colors || [],
    sizes: reqData.sizes || [],
    minPrice: reqData.minPrice || 0,
    maxPrice: reqData.maxPrice || 999999,
    minDiscount: reqData.minDiscount || 0,
    sort: reqData.sort || "price_low",
    stock: reqData.stockValue || "in_stock",
    pageNumber: reqData.pageNumber || 0,
    pageSize:  10,
    firstCategory: reqData.firstCategory || "erkek",
    secondCategory: reqData.secondCategory || "giyim",
    thirdCategory: reqData.thirdCategory || "tisort"

  }

  try {


    const responsex = await apiGet(`/api/products?colors=${defaultSearchParam.color}&sizes=${defaultSearchParam.sizes}&minPrice=${defaultSearchParam.minPrice}&maxPrice=${defaultSearchParam.maxPrice}&minDiscount=${defaultSearchParam.minDiscount}&sort=${defaultSearchParam.sort}&stock=${defaultSearchParam.stock}&pageNumber=${defaultSearchParam.pageNumber}&pageSize=${defaultSearchParam.pageSize}&firstCategory=${defaultSearchParam.firstCategory}&secondCategory=${defaultSearchParam.secondCategory}&thirdCategory=${defaultSearchParam.thirdCategory}`);

  
 

    dispatch({ type: FIND_PRODUCTS_SUCCESS, payload: responsex.data });
  } catch (error) {
    dispatch({ type: FIND_PRODUCTS_FAILURE, payload: error.message });
  }
};

export const findProductById = (reqData) => async (dispatch) => {
  dispatch({ type: FIND_PPRODUCT_BY_ID_REQUEST });

  const  productId  = reqData;
  try {
  
    const responsex = await apiGet(`/api/products/id/${productId}`)
    console.log("xx ",responsex.data)

    dispatch({ type: FIND_PPRODUCT_BY_ID_SUCCESS, payload: responsex.data });
  } catch (error) {
    dispatch({ type: FIND_PPRODUCT_BY_ID_FAILURE, payload: error.message });
  }
};


/* Örnek bir api isteği */
/* http://localhost:5454/api/products?category=T-shirt&colors=&sizes=0&minPrice=0&maxPrice=99999&minDiscount=0&sort=price_low&stock=in_stock&pageNumber=0&pageSize=10 */