import { useEffect, useState } from "react";
import { RadioGroup } from "@headlessui/react";
import Rating from "@mui/material/Rating";
import { Alert, Box, Button, Grid, LinearProgress } from "@mui/material";
import ProductReviewCard from "./ProductReviewCard";
import { erkek_takim } from "../../../Data/erkek_takim";
import HomeSectionCarousel from "../HomeSectionCarousel/HomeSectionCarousel";
import { useNavigate, useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { findProductById } from "../../../State/Product/Action";
import { styled } from "@mui/system";
import { addCartItem } from "../../../State/Cart/Action";
const product = {
  name: "Basic Tee 6-Pack",
  price: "$192",
  href: "#",
  breadcrumbs: [
    { id: 1, name: "Men", href: "#" },
    { id: 2, name: "Clothing", href: "#" },
  ],
  images: [
    {
      src: "https://tailwindui.com/img/ecommerce-images/product-page-02-secondary-product-shot.jpg",
      alt: "Two each of gray, white, and black shirts laying flat.",
    },
    {
      src: "https://tailwindui.com/img/ecommerce-images/product-page-02-tertiary-product-shot-01.jpg",
      alt: "Model wearing plain black basic tee.",
    },
    {
      src: "https://tailwindui.com/img/ecommerce-images/product-page-02-tertiary-product-shot-02.jpg",
      alt: "Model wearing plain gray basic tee.",
    },
    {
      src: "https://tailwindui.com/img/ecommerce-images/product-page-02-featured-product-shot.jpg",
      alt: "Model wearing plain white basic tee.",
    },
  ],
  sizes: [
    { name: "Small", inStock: false, value: "SMALL" },
    { name: "Medium", inStock: false, value: "MEDIUM" },
    { name: "Large", inStock: false, value: "LARGE" },
  ],
  colors: [
    { name: "Siyah", inStock: false, value: "SIYAH", hex: "#000000" },
    { name: "Beyaz", inStock: false, value: "BEYAZ", hex: "#FFFFFF" },
    { name: "Kırmızı", inStock: false, value: "KIRMIZI", hex: "#FF0000" },
    { name: "Yeşil", inStock: false, value: "YESIL", hex: "#00FF00" },
    { name: "Mavi", inStock: false, value: "MAVI", hex: "#0000FF" },
    { name: "Sarı", inStock: false, value: "SARI", hex: "#FFFF00" },
    { name: "Turuncu", inStock: false, value: "TURUNCU", hex: "#FFA500" },
    { name: "Mor", inStock: false, value: "MOR", hex: "#800080" },
    { name: "Pembe", inStock: false, value: "PEMBE", hex: "#FFC0CB" },
    { name: "Kahverengi", inStock: false, value: "KAHVERENGI", hex: "#8B4513" },
    { name: "Gri", inStock: false, value: "GRI", hex: "#808080" },
    { name: "Cyan", inStock: false, value: "CYAN", hex: "#00FFFF" },
    { name: "Magenta", inStock: false, value: "MAGENTA", hex: "#FF00FF" },
  ],
  description:
    'The Basic Tee 6-Pack allows you to fully express your vibrant personality with three grayscale options. Feeling adventurous? Put on a heather gray tee. Want to be a trendsetter? Try our exclusive colorway: "Black". Need to add an extra pop of color to your outfit? Our white tee has you covered.',
  highlights: [
    "Yüksek kaliteli malzemeler kullanılarak üretilmiştir, uzun ömürlü ve konforlu bir giyim deneyimi sunar.",
    "Trendy ve şık tasarımı ile dikkat çeker, güncel moda trendlerini yansıtarak stilinizi tamamlar.",
    "Esnek dokular ve rahat kesimler sayesinde gün boyu konforlu bir giyim deneyimi sunar.",
    "Makinede yıkanabilir veya ütü gerektirmeyen özellikleriyle pratik ve kullanımı kolaydır.",
  ],
  details:
    "Bu zarif kadın elbisesi, şıklığı ve rahatlığı bir araya getiriyor. Yumuşak ve konforlu kumaşı ile gün boyu rahatlık sağlarken, zarif kesimi ile stilinizi tamamlıyor. Bel kısmındaki ince kemer detayı, figürünüze zarif bir vurgu eklerken, çıkarılabilir olması ile de kullanım esnekliği sunuyor. Klasik renk seçenekleri arasında siyah, beyaz ve canlı renkler bulunuyor. Her gün kullanıma uygunluğu ile öne çıkan bu elbise, şıklığı her anınıza taşıyor.",
};

const reviews = { href: "#", average: 4, totalCount: 117 };

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

export default function ProductDetails() {
  const [selectedColor, setSelectedColor] = useState("");
  const [selectedSize, setSelectedSize] = useState("");
  const [urun, setUrun] = useState();
  const [defaultUrun, setDefaultUrun] = useState(product);
  const [warning, setWarning] = useState(false);

  const { param1 } = useParams();
  const dispatch = useDispatch();

  const productx = useSelector((state) => state.product.product);

  const navigate = useNavigate();

  const handleAddToCart = () => {
   
    if(!selectedColor.value || !selectedSize.value){
      setWarning(true);
    }else{
      setWarning(false);
      console.log(selectedColor.value);
      console.log(selectedSize.value);
      const request={
        productId : productx.id,
        size : selectedSize.value,
        color : selectedColor.value
      }
      dispatch(addCartItem(request))
      navigate("/cart");
    }
  };

  const colorStockChecker = (colorValue) => {
    setDefaultUrun((prevUrun) => {
      const updatedColors = prevUrun.colors.map((color) =>
        color.value === colorValue ? { ...color, inStock: true } : color
      );

      return { ...prevUrun, colors: updatedColors };
    });

    console.log("birtakım bişeyler");
  };

  const sizeStockChecker = (sizeValue) => {
    setDefaultUrun((prevUrun) => {
      const updatedSizes = prevUrun.sizes.map((size) =>
        size.value === sizeValue ? { ...size, inStock: true } : size
      );
      return { ...prevUrun, sizes: updatedSizes };
    });
  };

  /* TODO */
  useEffect(() => {
    console.log("param1: ", param1);

    dispatch(findProductById(param1));
  }, [param1, dispatch]);

  useEffect(() => {
    console.log("product: ", productx);
    setUrun(productx);

    if (productx && productx.properties) {
      productx.properties.forEach((color) => {
        console.log("31", color.color);
        colorStockChecker(color.color);
      });
    }
    if (productx && productx.properties) {
      productx.properties.forEach((size) => {
        console.log("31", size.size);

        sizeStockChecker(size.size);
      });
    }
  }, [productx, urun, warning]);

  function formatter(str) {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
  }
  sizeStockChecker;
  return (
    <>
      {productx ? (
        <div className="bg-white lg:px-20">
          <div className="pt-6">
            <nav aria-label="Breadcrumb">
              <ol
                role="list"
                className="mx-auto flex max-w-2xl items-center space-x-2 px-4 sm:px-6 lg:max-w-7xl lg:px-8"
              >
                <li>
                  <div className="flex items-center">
                    <a
                      href={`/${productx.categoryFirst}/${productx.categorySecond}/${productx.categoryThird}`}
                      className="mr-2 text-sm font-medium text-gray-900"
                    >
                      {formatter(productx.categoryFirst)}
                    </a>
                    <svg
                      width={16}
                      height={20}
                      viewBox="0 0 16 20"
                      fill="currentColor"
                      aria-hidden="true"
                      className="h-5 w-4 text-gray-300"
                    >
                      <path d="M5.697 4.34L8.98 16.532h1.327L7.025 4.341H5.697z" />
                    </svg>
                  </div>
                </li>
                <li>
                  <div className="flex items-center">
                    <a
                      href={`/${productx.categoryFirst}/${productx.categorySecond}/${productx.categoryThird}`}
                      className="mr-2 text-sm font-medium text-gray-900"
                    >
                      {formatter(productx.categorySecond)}
                    </a>
                    <svg
                      width={16}
                      height={20}
                      viewBox="0 0 16 20"
                      fill="currentColor"
                      aria-hidden="true"
                      className="h-5 w-4 text-gray-300"
                    >
                      <path d="M5.697 4.34L8.98 16.532h1.327L7.025 4.341H5.697z" />
                    </svg>
                  </div>
                </li>
                <li>
                  <div className="flex items-center">
                    <a
                      href={`/${productx.categoryFirst}/${productx.categorySecond}/${productx.categoryThird}`}
                      className="mr-2 text-sm font-medium text-gray-900"
                    >
                      {formatter(productx.categoryThird)}
                    </a>
                    <svg
                      width={16}
                      height={20}
                      viewBox="0 0 16 20"
                      fill="currentColor"
                      aria-hidden="true"
                      className="h-5 w-4 text-gray-300"
                    >
                      <path d="M5.697 4.34L8.98 16.532h1.327L7.025 4.341H5.697z" />
                    </svg>
                  </div>
                </li>
                <li className="text-sm">
                  <a
                    href={`/${productx.categoryFirst}/${productx.categorySecond}/${productx.categoryThird}`}
                    aria-current="page"
                    className="font-medium text-gray-500 hover:text-gray-600"
                  >
                    {productx.title}
                  </a>
                </li>
              </ol>
            </nav>
            <section className="grid grid-cols-1 lg:grid-cols-2 gap-x-8 gap-y-10 px-4 pt-10">
              {/* Image gallery */}

              <div className="flex flex-col items-center">
                <div className="overflow-hidden rounded-lg max-w-[30rem] max-h-[35rem]">
                  <img
                    src={productx.imageUrl}
                    alt={""}
                    className="h-full w-full object-cover object-center"
                  />
                </div>
                {/* En boy oranlama çalışmıyor fotograflar bozulup ekranda gözükmüyor : aspect-h-2 aspect-w-3 */}
                {/* Ürün fotografıları altındaki küçül fotograflar */}
                {/* <div className="flex flex-wrap space-x-5 justify-center">
                  {product.images.map((item) => (
                    <div className=" overflow-hidden rounded-lg max-w-[5rem] max-h-[5rem] mt-4">
                      <img
                        src={item.src}
                        alt={item.alt}
                        className="h-full w-full object-cover object-center"
                      />
                    </div>
                  ))}
                </div> */}
              </div>
              {/* Product info */}
              <div className="lg:col-span-1 maxt-auto max-w-2xl px-4 pb-16 sm:px-6 lg:max-w-7xl lg:px-8 lg:pb-24">
                <div className="lg:col-span-2 ">
                  <h1 className="text-lg lg:text-xl font-semibold text-gray-900">
                    {productx.brand}
                  </h1>
                  <h1 className="text-lg lg:text-xl text-gray-900 opacity-60 pt-1">
                    {productx.title}
                  </h1>
                </div>

                {/* Options */}
                <div className="mt-4 lg:row-span-3 lg:mt-0">
                  <h2 className="sr-only">{productx.description}</h2>
                  <div className="flex space-x-5 items-center text-lg lg:text-xl text-gray-900 mt-6">
                    <p className="font-semibond">
                      {productx.discountedPrice} ₺
                    </p>
                    <p className="opacity-50 line-through">
                      {" "}
                      {productx.price} ₺{" "}
                    </p>
                    <p className="text-green-600 font-semibond">
                      %{productx.discountPercent} indirim{" "}
                    </p>
                  </div>

                  {/* Reviews */}
                  <div className="mt-6">
                    <div className="flex items-center space-x-3">
                      <Rating
                        name="half-rating-read"
                        value={3.5}
                        precision={0.5}
                        readOnly
                      />
                      <p className="opacity-50 text-sm">
                        {productx.ratings.size} Ratings
                      </p>
                      <p className="ml-3 text-sm font-medium text-indigo-600 hover:text-indigo-500">
                        {productx.reviews.size} Reviews
                      </p>
                    </div>
                  </div>

                  <form className="mt-10">
                    {/* Colors */}
                    <div className="mt-10 mb-10">
                      <div className="flex items-center justify-between">
                        <h3 className="text-sm font-medium text-gray-900">
                          Renkler
                        </h3>
                      </div>

                      <RadioGroup
                        value={selectedColor}
                        onChange={setSelectedColor}
                        className="mt-4"
                      >
                        <RadioGroup.Label className="sr-only">
                          Bir renk seç
                        </RadioGroup.Label>
                        <div className="grid grid-cols-4 gap-2 sm:grid-cols-12 lg:grid-cols-6">
                          {defaultUrun.colors.map((color) => (
                            <RadioGroup.Option
                              key={color.name}
                              value={color}
                              disabled={!color.inStock}
                              style={{ backgroundColor: `${color.hex}` }}
                              className={({ active }) =>
                                classNames(
                                  color.inStock
                                    ? `cursor-pointer bg-white text-gray-900 shadow-sm `
                                    : "cursor-not-allowed bg-gray-50 text-gray-200",
                                  active ? "ring-2 ring-indigo-500" : "",
                                  "group relative flex items-center justify-center rounded-md border py-3 px-4 text-sm font-medium uppercase hover:bg-gray-50 focus:outline-none sm:flex-1 sm:py-6"
                                )
                              }
                            >
                              {({ active, checked }) => (
                                <>
                                  <RadioGroup.Label as="span">
                                    {}
                                  </RadioGroup.Label>
                                  {color.inStock ? (
                                    <span
                                      className={classNames(
                                        active ? "border" : "border-2",
                                        checked
                                          ? "border-indigo-500"
                                          : "border-transparent",
                                        "pointer-events-none absolute -inset-px rounded-md"
                                      )}
                                      aria-hidden="true"
                                    />
                                  ) : (
                                    <span
                                      aria-hidden="true"
                                      className="pointer-events-none absolute -inset-px rounded-md border-2 border-gray-200"
                                    >
                                      <svg
                                        className="absolute inset-0 h-full w-full stroke-2 text-gray-200"
                                        viewBox="0 0 100 100"
                                        preserveAspectRatio="none"
                                        stroke="currentColor"
                                      >
                                        <line
                                          x1={0}
                                          y1={100}
                                          x2={100}
                                          y2={0}
                                          vectorEffect="non-scaling-stroke"
                                        />
                                      </svg>
                                    </span>
                                  )}
                                </>
                              )}
                            </RadioGroup.Option>
                          ))}
                        </div>
                      </RadioGroup>
                    </div>
                    {/* Sizes */}
                    <div className="mt-10 mb-10">
                      <div className="flex items-center justify-between">
                        <h3 className="text-sm font-medium text-gray-900">
                          Bedenler
                        </h3>
                      </div>

                      <RadioGroup
                        value={selectedSize}
                        onChange={setSelectedSize}
                        className="mt-4"
                      >
                        <RadioGroup.Label className="sr-only">
                          Choose a size
                        </RadioGroup.Label>
                        <div className="grid grid-cols-4 gap-4 sm:grid-cols-8 lg:grid-cols-4">
                          {defaultUrun.sizes.map((size) => (
                            <RadioGroup.Option
                              key={size.name}
                              value={size}
                              disabled={!size.inStock}
                              className={({ active }) =>
                                classNames(
                                  size.inStock
                                    ? "cursor-pointer bg-white text-gray-900 shadow-sm"
                                    : "cursor-not-allowed bg-gray-50 text-gray-200",
                                  active ? "ring-2 ring-indigo-500" : "",
                                  "group relative flex items-center justify-center rounded-md border py-3 px-4 text-sm font-medium uppercase hover:bg-gray-50 focus:outline-none sm:flex-1 sm:py-6"
                                )
                              }
                            >
                              {({ active, checked }) => (
                                <>
                                  <RadioGroup.Label as="span">
                                    {size.name}
                                  </RadioGroup.Label>
                                  {size.inStock ? (
                                    <span
                                      className={classNames(
                                        active ? "border" : "border-2",
                                        checked
                                          ? "border-indigo-500"
                                          : "border-transparent",
                                        "pointer-events-none absolute -inset-px rounded-md"
                                      )}
                                      aria-hidden="true"
                                    />
                                  ) : (
                                    <span
                                      aria-hidden="true"
                                      className="pointer-events-none absolute -inset-px rounded-md border-2 border-gray-200"
                                    >
                                      <svg
                                        className="absolute inset-0 h-full w-full stroke-2 text-gray-200"
                                        viewBox="0 0 100 100"
                                        preserveAspectRatio="none"
                                        stroke="currentColor"
                                      >
                                        <line
                                          x1={0}
                                          y1={100}
                                          x2={100}
                                          y2={0}
                                          vectorEffect="non-scaling-stroke"
                                        />
                                      </svg>
                                    </span>
                                  )}
                                </>
                              )}
                            </RadioGroup.Option>
                          ))}
                        </div>
                      </RadioGroup>
                    </div>

                    <Button
                      onClick={handleAddToCart}
                      variant="contained"
                      sx={{
                        px: "2rem",
                        py: "1rem",
                        backgroundColor: "#9155fd",
                      }}
                    >
                      Sepete Ekle
                    </Button>
                    {warning && (
                      <div className="mt-6">
                        <Alert variant="outlined" severity="warning">
                          Ürünü sepete ekleyebilmek içik mevcut renk ve
                          bedenlerden birini seçmelisiniz.
                        </Alert>
                      </div>
                    )}
                  </form>
                </div>

                <div className="py-10 lg:col-span-2 lg:col-start-1 lg:border-r lg:border-gray-200 lg:pb-16 lg:pr-8 lg:pt-6">
                  {/* Description and details */}
                  <div>
                    <h3 className="sr-only">Açıklama</h3>

                    <div className="space-y-6">
                      <p className="text-base text-gray-900">
                        {productx.description}
                      </p>
                    </div>
                  </div>

                  <div className="mt-10">
                    <h3 className="text-sm font-medium text-gray-900">
                      Öne çıkan özellikler
                    </h3>

                    <div className="mt-4">
                      <ul
                        role="list"
                        className="list-disc space-y-2 pl-4 text-sm"
                      >
                        {product.highlights.map((highlight) => (
                          <li key={highlight} className="text-gray-400">
                            <span className="text-gray-600">{highlight}</span>
                          </li>
                        ))}
                      </ul>
                    </div>
                  </div>

                  <div className="mt-10">
                    <h2 className="text-sm font-medium text-gray-900">
                      Açıklama
                    </h2>

                    <div className="mt-4 space-y-6">
                      <p className="text-sm text-gray-600">{product.details}</p>
                    </div>
                  </div>
                </div>
              </div>
            </section>
            {/* Similar product */}
            <section className="pt-10 pb-10">
              <HomeSectionCarousel
                data={erkek_takim}
                sectionName={"Benzer ürünler "}
              />
            </section>
            {/* People who bought this product also bought these */}
            <section className="pt-10 pb-10">
              <HomeSectionCarousel
                data={erkek_takim}
                sectionName={"Bu ürünü alanlar bunları da aldı "}
              />
            </section>
            {/* Rating and reviews */}
            <section>
              <h1 className="font-semibond text-lg pb-4">
                Recent review & Rating
              </h1>
              <div className="border p-5">
                <Grid container spacing={7}>
                  <Grid item xs={7}>
                    <div className="space-y-5">
                      {[1, 1, 1, 1].map((item) => (
                        <ProductReviewCard />
                      ))}
                    </div>
                  </Grid>
                  <Grid item xs={5}>
                    <h1 className="text-xl font-semibold pb-2">
                      Product Ratings
                    </h1>
                    <div className="flex items-center space-x-3">
                      <Rating name="read-only" value={3.6} precision={0.5} />
                      <p className="opacity-60">3131 Ratings</p>
                    </div>

                    <Box className="mt-5 space-y-5">
                      <Grid container alignItems="center" gap={2}>
                        <Grid item xs={2}>
                          <p>Excellent</p>
                        </Grid>
                        <Grid item xs={7}>
                          <LinearProgress
                            sx={{
                              bgcolor: "#d0d0d0",
                              borderRadius: 4,
                              height: 7,
                              "& .MuiLinearProgress-bar": {
                                bgcolor: "#4CAF50",
                              },
                            }}
                            variant="determinate"
                            value={40}
                          />
                        </Grid>
                      </Grid>
                      <Grid container alignItems="center" gap={2}>
                        <Grid item xs={2}>
                          <p>Very Good</p>
                        </Grid>
                        <Grid item xs={7}>
                          <LinearProgress
                            sx={{
                              bgcolor: "#d0d0d0",
                              borderRadius: 4,
                              height: 7,
                              "& .MuiLinearProgress-bar": {
                                bgcolor: "#8BC34A",
                              },
                            }}
                            variant="determinate"
                            value={30}
                          />
                        </Grid>
                      </Grid>
                      <Grid container alignItems="center" gap={2}>
                        <Grid item xs={2}>
                          <p>Good</p>
                        </Grid>
                        <Grid item xs={7}>
                          <LinearProgress
                            sx={{
                              bgcolor: "#d0d0d0",
                              borderRadius: 4,
                              height: 7,
                              "& .MuiLinearProgress-bar": {
                                bgcolor: "#FFC107",
                              },
                            }}
                            variant="determinate"
                            value={25}
                          />
                        </Grid>
                      </Grid>
                      <Grid container alignItems="center" gap={2}>
                        <Grid item xs={2}>
                          <p>Avarage</p>
                        </Grid>
                        <Grid item xs={7}>
                          <LinearProgress
                            sx={{
                              bgcolor: "#d0d0d0",
                              borderRadius: 4,
                              height: 7,
                              "& .MuiLinearProgress-bar": {
                                bgcolor: "#FF9800",
                              },
                            }}
                            variant="determinate"
                            value={15}
                          />
                        </Grid>
                      </Grid>
                      <Grid container alignItems="center" gap={2}>
                        <Grid item xs={2}>
                          <p>Poor</p>
                        </Grid>
                        <Grid item xs={7}>
                          <LinearProgress
                            sx={{
                              bgcolor: "#d0d0d0",
                              borderRadius: 4,
                              height: 7,
                              "& .MuiLinearProgress-bar": {
                                bgcolor: "#F44336",
                              },
                            }}
                            variant="determinate"
                            value={15}
                          />
                        </Grid>
                      </Grid>
                    </Box>
                  </Grid>
                </Grid>
              </div>
            </section>
          </div>
        </div>
      ) : (
        <div>Ürün yüklenemedi</div>
      )}
    </>
  );
}
