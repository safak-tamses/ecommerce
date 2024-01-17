import React from "react";
import AliceCarousel from "react-alice-carousel";
import "react-alice-carousel/lib/alice-carousel.css";
import { mainCarouselData } from "./MainCarouselData";

const MainCarousel = () => {
  const items = mainCarouselData.map((item, index) => (
    <div className="flex items-center justify-center -z-20" key={index}>
      <img className="cursor-pointer w-full h-auto" src={item.image} alt="" />
    </div>
  ));

  return (
    <AliceCarousel
      items={items}
      infinite
      disableButtonsControls
      autoPlay
      autoPlayInterval={1000}
    />
  );
};

export default MainCarousel;
