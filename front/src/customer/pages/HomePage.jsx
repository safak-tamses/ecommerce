import React, { useEffect, useState } from "react";
import MainCarousel from "../components/HomeCarousel/MainCarousel";
import HomeSectionCarousel from "../components/HomeSectionCarousel/HomeSectionCarousel";
import { erkek_takim } from "../../Data/erkek_takim";
import { useDispatch, useSelector } from "react-redux";
import { findProducts } from "../../State/Product/Action";

const HomePage = () => {
  const product = useSelector((state) => state.product.products);

  const dispatch = useDispatch();
  const [colection, setCollection] = useState();

  useEffect(() => {
    const data = {
      colors: "",
      sizes: "",
      minPrice: 0,
      maxPrice: 99999,
      discountValue: "",
      sort: "price_low",
      pageNumber: 0,
      pageSize: 10,
      stockValue: "in_stock",
      firstCategory: "kadin",
      secondCategory: "giyim",
      thirdCategory: "elbise",
    };

    dispatch(findProducts(data));
  }, [dispatch]);

  useEffect(() => {
    if (product) {
      setCollection(product.content);
    }
    console.log("birmalumbir:",colection)
  }, [product, colection]);

  return (
    <div>
      <MainCarousel />
      <div className="space-y-10 py-20 flex flex-col justify-center px-5 lg:px-10">
        {colection ? (
          <HomeSectionCarousel
            data={colection}
            sectionName={"YENİ KOLEKSİYON"}
          />
        ) : (
          <></>
        )}
        {colection ? (
          <HomeSectionCarousel
            data={colection}
            sectionName={"KADIN GİYİM"}
          />
        ) : (
          <></>
        )}
        {colection ? (
          <HomeSectionCarousel
            data={colection}
            sectionName={"KAÇIRILMAYACAK FIRSATLAR"}
          />
        ) : (
          <></>
        )}
        {colection ? (
          <HomeSectionCarousel
            data={colection}
            sectionName={"SİZE ÖZEL ÜRÜNLER"}
          />
        ) : (
          <></>
        )}
      </div>
    </div>
  );
};

export default HomePage;
