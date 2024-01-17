import React from "react";
import { Route, Routes, useParams } from "react-router-dom";
import HomePage from "../customer/pages/HomePage";
import Cart from "../customer/components/Cart/Cart";
import Navigation from "../customer/components/Navigation/Navigation";
import Footer from "../customer/components/Footer/Footer";
import Product from "../customer/components/Product/Product";
import ProductDetails from "../customer/components/ProductDetails/ProductDetails";
import Checkout from "../customer/components/Checkout/Checkout";
import Order from "../customer/components/Order/Order";
import OrderDetails from "../customer/components/Order/OrderDetails";

const CustomerRouters = () => {
  return (
    <div>
      <div>
        <Navigation />
      </div>
      <div className="w-10/12 mx-auto">
        <Routes>
          <Route path="/login" element={<HomePage />}></Route>
          <Route path="/register" element={<HomePage />}></Route>

          <Route path="/" element={<HomePage />}></Route>
          <Route path="/cart" element={<Cart />}></Route>

          <Route
            path="/:param1/:param2/:param3"
            element={
              <Product
                param1={useParams().param1}
                param2={useParams().param2}
                param3={useParams().param3}
              />
            }
          />

          <Route
            path="/product/:param1"
            element={<ProductDetails param1={useParams().param1} />}
          ></Route>
          <Route path="/checkout" element={<Checkout />}></Route>
          <Route path="/account/order" element={<Order />}></Route>
          <Route
            path="/account/order/:orderId"
            element={<OrderDetails />}
          ></Route>
        </Routes>
      </div>

      <div>
        <Footer />
      </div>
    </div>
  );
};

export default CustomerRouters;
