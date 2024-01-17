import React from "react";
import PropTypes from "prop-types";

const HomeSectionCard = ({ product, onClick }) => {
  const titleLimit = 20;
  const truncatedTitle =
    product.title.length > titleLimit
      ? product.title.slice(0, titleLimit) + "..."
      : product.title;

  const handleCardClick = () => {
    onClick(product.id); // Tıklanan ürünün adresini gönder
  };

  return (
    <div
      className="cursor-pointer flex flex-col items-center bg-white rounded-lg shadow-lg overflow-hidden w-[15rem] mx-3"
      onClick={handleCardClick}
    >
      <div className="h-[13rem] w-[10rem] overflow-hidden">
        <img
          className="object-cover object-center w-full h-full"
          src={product.imageUrl}
          alt=""
        />
      </div>
      <div className="p-4">
        <h3 className="text-lg font-medium text-gray-900">{product.brand}</h3>
        <p className="mt-2 text-sm text-gray-500">{truncatedTitle}</p>
      </div>
    </div>
  );
};

HomeSectionCard.propTypes = {
  product: PropTypes.object.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default HomeSectionCard;
