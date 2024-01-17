import { Button, IconButton } from "@mui/material";
import React from "react";
import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline";
import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import { color } from "../Product/FilterData";
const CartItem = ({ cartItem }) => {
  return (
    <>
      <div className="p-5 shadow-lg border rounded-md mb-6">
        <div className="flex items-center ">
          <div className="w-[5rem] h-[5rem] lg:w-[9rem] lg:h-[9rem]">
            <img
              className="w-full h-full object-cover object-top"
              src={cartItem.product.imageUrl}
              alt=""
            />
          </div>
          <div className="ml-5 space-y-1">
            <p className="font-semibold">Ürun: {cartItem.product.title}</p>
            <p className="font-semibold">Marka: {cartItem.product.brand}</p>
            <p className="opacity-70 ">Beden: {cartItem.size}</p>
            <p className="opacity-70 mt-2">Renk: {cartItem.color}</p>
            <div className="flex space-x-5 items-center text-gray-900 ">
              <p className="opacity-50 line-through">{cartItem.price} ₺</p>
              <p className="font-semibold">{cartItem.discountedPrice} ₺</p>
            </div>
          </div>
        </div>
        <div className="lg:flex items-center lg:space-x-10 pt-4">
          <div className="flex items-center space-x-2">
            <IconButton sx={{ color: "#f87c1c" }}>
              <RemoveCircleOutlineIcon />
            </IconButton>
            <span className="py-1 px-7 border rounded-sm">
              {cartItem.quantity}
            </span>
            <IconButton sx={{ color: "#f87c1c" }}>
              <AddCircleOutlineIcon />
            </IconButton>
          </div>
          <div>
            <Button sx={{ "&:hover": { "& svg": { color: "#f87c1c" } } }}>
              <DeleteOutlineIcon sx={{ color: "#9E9E9E" }} />
            </Button>
          </div>
        </div>
      </div>
    </>
  );
};

export default CartItem;
