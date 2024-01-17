import React, { useEffect, useState } from "react";
import CartItem from "./CartItem";
import { Button, Divider } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { getCart } from "../../../State/Cart/Action";

const Cart = () => {
  const [cart, setCart] = useState();
  const [toplamTutar, setToplamTutar] = useState();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const handleCheckout = () => {
    navigate("/checkout?step=2");
  };
  const cartx = useSelector((state) => state.cart.cart);
  useEffect(() => {
    dispatch(getCart());
  }, [dispatch]);

  useEffect(() => {
    if (cartx) {
      setCart(cartx);
      console.log("Cart componenti içindeki sepet ürünü", cart);
      if (cartx.totalDiscountedPrice) {
        setToplamTutar(kdvHesapla(cartx.totalDiscountedPrice).toFixed(2));
      }
    }
  }, [cartx, cart, toplamTutar]);
  function kdvHesapla(ucret) {
    var kdvOrani = 0.18;
    var kdv = ucret * kdvOrani;
    var toplamTutar = ucret + kdv;
    return toplamTutar;
  }

  return (
    <>
      {cartx ? (
        <div className="">
          <div className="lg:grid grid-cols-3 lg:px-16 relative mt-8">
            <div className="col-span-2 mb-8">
              {cartx.cartItems.map((item) => (
                <CartItem cartItem={item} />
              ))}
            </div>
            <div className="px-5 sticky top-0 h-[100vh] mt-5 lg:mt-0">
              <div className="border p-5">
                <p className="uppercase folt-bold opacity-60 pb-4">
                  Sipariş Özeti
                </p>
                <hr />
                <div className="space-y-3 font-semibold mb-10">
                  <div className="flex justify-between pt-3 text-black">
                    <span>Ürünün Toplamı</span>
                    <span>{cartx.totalPrice}₺</span>
                  </div>
                  <div className="flex justify-between pt-3 ">
                    <span>İndirimli Fiyat</span>
                    <span className="text-green-600">
                      {cartx.totalDiscountedPrice.toFixed(2)}₺
                    </span>
                  </div>
                  <div className="flex justify-between pt-3 ">
                    <span>Kargo Toplam</span>
                    <span className="text-green-600">Ücretsiz</span>
                  </div>
                  <div className="flex justify-between pt-3 ">
                    <span>KDV tutarı</span>
                    <span className="text-green-600">%18</span>
                  </div>
                  <div className="flex justify-between pt-3 font-bold">
                    <span>Toplam</span>
                    <span className="text-green-600">{toplamTutar}₺</span>
                  </div>
                </div>
                <Button
                  onClick={handleCheckout}
                  variant="contained"
                  className="w-full mt-5"
                  sx={{ px: "2.5rem", py: "0.7rem", bgcolor: "#9155fd" }}
                >
                  Sepeti Onayla
                </Button>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <div>Sepetinizde ürün yok</div>
      )}
    </>
  );
};

export default Cart;
