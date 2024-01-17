import { useEffect, useState } from "react";
import "./ProductCard.css";
import { useNavigate } from "react-router-dom";

const ProductCard = ({ product }) => {
  const navigate = useNavigate();
  const [isHovered, setIsHovered] = useState(false);
  const slidedText = product.title.split(' ').slice(0, 3).join(' ');

  useEffect(() => {
    console.log("param1:", isHovered);
  }, [isHovered]);

  return (
    <div
      onClick={() => navigate(`/product/${product.id}`)}
      className={`productCard w-[15rem] m-3 transition-all cursor-pointer p-4 `}
      onMouseOver={() => setIsHovered(true)}
      onMouseOut={() => setIsHovered(false)}
    >
      <div className="h-[20rem]">
        <img
          className="h-full w-full object-cover object-left-top "
          src={product.imageUrl}
          alt=""
          style={{ objectFit: "contain" }}
        />
      </div>

      <div className={`textPart bg-white p-3 ${isHovered ? "hovered" : ""}`}>
        <div>
          <p className="font-bold opacity-60">{product.brand}</p>
          {isHovered ? (
            <p className="">{product.title}</p>
          ) : (
            <p className="hovered-text">{slidedText}</p>
          )}
        </div>

        <div className="flex items-center space-x-4">
          <p className="line-through opacity-50">{product.price}</p>
          <p className="text-green-600 font-semibold">
            %{product.discountPercent} indirim
          </p>
        </div>
        <div>
          <p className="font-semibond text-orange-500 text-xl">
            {product.discountedPrice} ₺
          </p>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
