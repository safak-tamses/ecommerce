import { Fragment, useEffect, useState } from "react";
import { Dialog, Menu, Popover, Tab, Transition } from "@headlessui/react";
import {
  Bars3Icon,
  MagnifyingGlassIcon,
  ShoppingBagIcon,
  XMarkIcon,
} from "@heroicons/react/24/outline";
import logo from "../../../assets/alsa.svg";
import { useLocation, useNavigate } from "react-router-dom";
import AuthModal from "../../auth/AuthModal";
import { Avatar, Button } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { getUser, logout } from "../../../State/Auth/Action";
import { deepPurple } from "@mui/material/colors";
const navigation = {
  categories: [
    {
      id: "kadin",
      name: "Kadın",
      featured: [
        {
          name: "Yeni Gelenler",
          href: "#",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-category-01.jpg",
          imageAlt:
            "Models sitting back to back, wearing Basic Tee in black and bone.",
        },
        {
          name: "Fırsat Ürünleri",
          href: "#",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-category-02.jpg",
          imageAlt:
            "Close up of Basic Tee fall bundle with off-white, ochre, olive, and black tees.",
        },
      ],
      sections: [
        {
          id: "giyim",
          name: "Giyim",
          items: [
            { name: "Elbise", value: "elbise" },
            { name: "Tişört", value: "tisort" },
            { name: "Gömlek", value: "gomlek" },
            { name: "Kot Pantalon", value: "kot-pantalon" },
            { name: "Kot Ceket", value: "kot-ceket" },
            { name: "Sweatshirt", value: "sweatshirt" },
            { name: "Şort", value: "sort" },
            { name: "Ceket", value: "ceket" },
            { name: "Eşofman Takımı", value: "esofman-takimi" },
            { name: "Kış Montu", value: "kis-montu" },
          ],
        },
        {
          id: "aksesuar",
          name: "Aksesuar",
          items: [
            { name: "Saat", value: "saat" },
            { name: "Bilezik", value: "bilezik" },
            { name: "Kolye", value: "kolye" },
            { name: "Gözlük", value: "gozluk" },
            { name: "Şapka", value: "sapka" },
            { name: "Cüzdan", value: "cuzdan" },
            { name: "Saat Kordonu", value: "saat-kordonu" },
            { name: "Gözlük Kılıfı", value: "gozluk-kilifi" },
            { name: "Broş", value: "bros" },
            { name: "Saç Bandı", value: "sac-bandi" },
          ],
        },
        {
          id: "ayakkabi",
          name: "Ayakkabı",
          items: [
            { name: "Spor Ayakkabı", value: "spor-ayakkabi" },
            { name: "Günlük Ayakkabı", value: "gunluk-ayakkabi" },
            { name: "Kış Botu", value: "kis-botu" },
            { name: "Topuklu Ayakkabı", value: "topuklu-ayakkabi" },
            { name: "Sandalet", value: "sandalet" },
            { name: "Terlik", value: "terlik" },
            { name: "Spor Sandalet", value: "spor-sandalet" },
            { name: "Babet", value: "babet" },
            { name: "Çizme", value: "cizme" },
            { name: "Outdoor Ayakkabı", value: "outdoor-ayakkabi" },
          ],
        },
        {
          id: "canta",
          name: "Çanta",
          items: [
            { name: "Omuz Çantası", value: "omuz-cantasi" },
            { name: "Sırt Çantası", value: "sirt-cantasi" },
            { name: "Cüzdan Çanta", value: "cuzdan-canta" },
            { name: "El Çantası", value: "el-cantasi" },
            { name: "Seyahat Çantası", value: "seyahat-cantasi" },
            { name: "Spor Çanta", value: "spor-canta" },
            { name: "Bilgisayar Çantası", value: "bilgisayar-cantasi" },
            { name: "Şehir Çantası", value: "sehir-cantasi" },
            { name: "Plaj Çantası", value: "plaj-cantasi" },
            { name: "Deri Çanta", value: "deri-canta" },
          ],
        },
      ],
    },
    {
      id: "erkek",
      name: "Erkek",
      featured: [
        {
          name: "Yeni Gelenler",
          href: "#",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-category-03.jpg",
          imageAlt:
            "Models sitting back to back, wearing Basic Tee in black and bone.",
        },
        {
          name: "Fırsat Ürünleri",
          href: "#",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-category-04.jpg",
          imageAlt:
            "Close up of Basic Tee fall bundle with off-white, ochre, olive, and black tees.",
        },
      ],
      sections: [
        {
          id: "giyim",
          name: "Giyim",
          items: [
            { name: "Gömlek", value: "gomlek" },
            { name: "Tişört", value: "tisort" },
            { name: "Pantolon", value: "pantolon" },
            { name: "Ceket", value: "ceket" },
            { name: "Kazak", value: "kazak" },
            { name: "Polo Yaka Tişört", value: "polo-yaka-tisort" },
            { name: "Sweatshirt", value: "sweatshirt" },
            { name: "Kaban", value: "kaban" },
            { name: "Eşofman Takımı", value: "esofman-takimi" },
            { name: "Jean Pantolon", value: "jean-pantolon" },
          ],
        },
        {
          id: "aksesuar",
          name: "Aksesuar",
          items: [
            { name: "Saat", value: "saat" },
            { name: "Kemer", value: "kemer" },
            { name: "Gözlük", value: "gozluk" },
            { name: "Şapka", value: "sapka" },
            { name: "Cüzdan", value: "cuzdan" },
            { name: "Kravat", value: "kravat" },
            { name: "Saat Kordonu", value: "saat-kordonu" },
            { name: "Eldiven", value: "eldiven" },
            { name: "Çorap", value: "corap" },
            { name: "Gömlek Düğmesi", value: "gomlek-dugmesi" },
          ],
        },
        {
          id: "ayakkabi",
          name: "Ayakkabı",
          items: [
            { name: "Spor Ayakkabı", value: "spor-ayakkabi" },
            { name: "Günlük Ayakkabı", value: "gunluk-ayakkabi" },
            { name: "Bot", value: "bot" },
            { name: "Loafer", value: "loafer" },
            { name: "Sandalet", value: "sandalet" },
            { name: "Spor Sandalet", value: "spor-sandalet" },
            { name: "Deri Ayakkabı", value: "deri-ayakkabi" },
            { name: "Çizme", value: "cizme" },
            { name: "Terlik", value: "terlik" },
            { name: "Koşu Ayakkabısı", value: "kosu-ayakkabisi" },
          ],
        },
        {
          id: "canta",
          name: "Çanta",
          items: [
            { name: "Sırt Çantası", value: "sirt-cantasi" },
            { name: "Seyahat Çantası", value: "seyahat-cantasi" },
            { name: "Cüzdan Çanta", value: "cuzdan-canta" },
            { name: "Kartlık", value: "kartlik" },
            { name: "Spor Çanta", value: "spor-canta" },
            { name: "Bilgisayar Çantası", value: "bilgisayar-cantasi" },
            { name: "Deri Çanta", value: "deri-canta" },
            { name: "Omuz Çantası", value: "omuz-cantasi" },
            { name: "Şehir Çantası", value: "sehir-cantasi" },
            { name: "Çapraz Askılı Çanta", value: "capraz-askili-canta" },
          ],
        },
      ],
    },
  ],
  pages: [
    { name: "Çok Satanlar", value: "cok-satanlar" },
    { name: "Fırsat Ürünleri", value: "firsat-urunleri" },
  ],
};


function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

export default function Navigation() {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();

  const [openAuthModel, setOpenAuthModal] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const openUserMenu = Boolean(anchorEl);
  const jwt = localStorage.getItem("jwt");
  const { auth } = useSelector((store) => store);
  const dispatch = useDispatch();
  const location = useLocation();

  const handleUserClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleCloseUserMenu = (event) => {
    setAnchorEl(null);
  };


  const handleOpen = () => {
    setOpenAuthModal(true);
    navigate("/login");
  };

  const handleClose = () => {
    setOpenAuthModal(false);
    navigate("/");
  };


  const handleLogout = () => {
    dispatch(logout());
    handleCloseUserMenu();
  };

  useEffect(() => {
    if (jwt) {
      dispatch(getUser(jwt));
    }
  }, [jwt, auth.jwt,dispatch]);

  useEffect(() => {
    if (auth.user) {
      setOpenAuthModal(false);
    }
  }, [auth.user, location.pathname, navigate]);

  return (
    <div className="bg-white">
      {/* Mobile menu */}
      <Transition.Root show={open} as={Fragment}>
        <Dialog as="div" className="relative z-40 lg:hidden" onClose={setOpen}>
          <Transition.Child
            as={Fragment}
            enter="transition-opacity ease-linear duration-300"
            enterFrom="opacity-0"
            enterTo="opacity-100"
            leave="transition-opacity ease-linear duration-300"
            leaveFrom="opacity-100"
            leaveTo="opacity-0"
          >
            <div className="fixed inset-0 bg-black bg-opacity-25" />
          </Transition.Child>

          <div className="fixed inset-0 z-40 flex">
            <Transition.Child
              as={Fragment}
              enter="transition ease-in-out duration-300 transform"
              enterFrom="-translate-x-full"
              enterTo="translate-x-0"
              leave="transition ease-in-out duration-300 transform"
              leaveFrom="translate-x-0"
              leaveTo="-translate-x-full"
            >
              <Dialog.Panel className="relative flex w-full max-w-xs flex-col overflow-y-auto bg-white pb-12 shadow-xl">
                <div className="flex px-4 pb-2 pt-5">
                  <button
                    type="button"
                    className="relative -m-2 inline-flex items-center justify-center rounded-md p-2 text-gray-400"
                    onClick={() => setOpen(false)}
                  >
                    <span className="absolute -inset-0.5" />
                    <span className="sr-only">Close menu</span>
                    <XMarkIcon className="h-6 w-6" aria-hidden="true" />
                  </button>
                </div>

                {/* Links */}
                <Tab.Group as="div" className="mt-2">
                  <div className="border-b border-gray-200">
                    <Tab.List className="-mb-px flex space-x-8 px-4">
                      {navigation.categories.map((category) => (
                        <Tab
                          key={category.name}
                          className={({ selected }) =>
                            classNames(
                              selected
                                ? "border-[#200c5c] text-[#200c5c]"
                                : "border-transparent text-gray-900",
                              "flex-1 whitespace-nowrap border-b-2 px-1 py-4 text-base font-medium"
                            )
                          }
                        >
                          {category.name}
                        </Tab>
                      ))}
                    </Tab.List>
                  </div>
                  <Tab.Panels as={Fragment}>
                    {navigation.categories.map((category) => (
                      <Tab.Panel
                        key={category.name}
                        className="space-y-10 px-4 pb-8 pt-10"
                      >
                        <div className="grid grid-cols-2 gap-x-4">
                          {category.featured.map((item) => (
                            <div
                              key={item.name}
                              className="group relative text-sm"
                            >
                              <div className="aspect-h-1 aspect-w-1 overflow-hidden rounded-lg bg-gray-100 group-hover:opacity-75">
                                <img
                                  src={item.imageSrc}
                                  alt={item.imageAlt}
                                  className="object-cover object-center"
                                />
                              </div>
                              <a
                                href={item.href}
                                className="mt-6 block font-medium text-gray-900"
                              >
                                <span
                                  className="absolute inset-0 z-10"
                                  aria-hidden="true"
                                />
                                {item.name}
                              </a>
                              <p aria-hidden="true" className="mt-1">
                                Shop now
                              </p>
                            </div>
                          ))}
                        </div>
                        {category.sections.map((section) => (
                          <div key={section.name}>
                            <p
                              id={`${category.id}-${section.id}-heading-mobile`}
                              className="font-medium text-gray-900"
                            >
                              {section.name}
                              
                            </p>
                            <ul
                              role="list"
                              aria-labelledby={`${category.id}-${section.id}-heading-mobile`}
                              className="mt-6 flex flex-col space-y-6"
                            >
                              {section.items.map((item) => (
                                <li key={item.name} className="flow-root">
                                  <a
                                  onClick={() => navigate(item.href)}
                                    href={item.href}
                                    className="-m-2 block p-2 text-gray-500"
                                    
                                  >
                                    {item.name}
                                  </a>
                                </li>
                              ))}
                            </ul>
                          </div>
                        ))}
                      </Tab.Panel>
                    ))}
                  </Tab.Panels>
                </Tab.Group>

                <div className="space-y-6 border-t border-gray-200 px-4 py-6">
                  {navigation.pages.map((page) => (
                    <div key={page.name} className="flow-root">
                      <a
                        href={page.href}
                        className="-m-2 block p-2 font-medium text-gray-900"
                      >
                        {page.name}
                      </a>
                    </div>
                  ))}
                </div>

                <div className="space-y-6 border-t border-gray-200 px-4 py-6">
                  <div className="flow-root">
                    <a
                      href="#"
                      className="-m-2 block p-2 font-medium text-gray-900"
                    >
                      Kayıt ol
                    </a>
                  </div>
                  <div className="flow-root">
                    <a
                      href="#"
                      className="-m-2 block p-2 font-medium text-gray-900"
                    >
                      Create account
                    </a>
                  </div>
                </div>

                <div className="border-t border-gray-200 px-4 py-6">
                  <a href="#" className="-m-2 flex items-center p-2">
                    <img
                      src="https://tailwindui.com/img/flags/flag-canada.svg"
                      alt=""
                      className="block h-auto w-5 flex-shrink-0"
                    />
                    <span className="ml-3 block text-base font-medium text-gray-900">
                      CAD
                    </span>
                    <span className="sr-only">, change currency</span>
                  </a>
                </div>
              </Dialog.Panel>
            </Transition.Child>
          </div>
        </Dialog>
      </Transition.Root>

      <header className="relative bg-white">
        <p
          className="flex h-10 items-center justify-center  px-4 text-sm font-medium text-white sm:px-6 lg:px-8"
          style={{ backgroundColor: "#200c5c" }}
        >
          550 TL ve Üzeri Alışverişlerinizde ÜCRETSİZ KARGO İmkanı
        </p>
        <p className="flex h-10 items-center justify-center  px-4 text-sm font-medium text-white sm:px-6 lg:px-8"
          style={{ backgroundColor: "#200c5c" }}>Giriş Yaptıktan sonra sayfayı yenileyiniz Ürünler Kadın/Giyim/Elbise katagori altındadır diğer ürünler şimdilik boş</p>

        <nav
          aria-label="Top"
          className="mx-auto max-w-8xl px-4 sm:px-6 lg:px-8"
        >
          <div className="border-b border-gray-200">
            <div className="flex h-16 items-center">
              <button
                type="button"
                className="relative rounded-md bg-white p-2 text-gray-400 lg:hidden"
                onClick={() => setOpen(true)}
              >
                <span className="absolute -inset-0.5" />
                <span className="sr-only">Open menu</span>
                <Bars3Icon className="h-6 w-6" aria-hidden="true" />
              </button>

              {/* Logo */}
              <div className="ml-4 flex lg:ml-0">
                <a href="/">
                  <span className="sr-only">Your Company</span>
                  <img className="h-16 w-auto" src={logo} alt="" />
                </a>
              </div>
              <div className="h-4/6 w-0.5 bg-slate-400 ml-8 flex items-center justify-center"></div>

              {/* Flyout menus */}
              <Popover.Group className="hidden lg:ml-8 lg:block lg:self-stretch">
                <div className="flex h-full space-x-8">
                  {navigation.categories.map((category) => (
                    <Popover key={category.name} className="flex">
                      {({ open }) => (
                        <>
                          <div className="relative flex ">
                            <Popover.Button
                              className={classNames(
                                open
                                  ? "border-[#200c5c] text-[#200c5c] sm:p-1"
                                  : "border-transparent text-gray-700 hover:text-gray-800 sm:p-1",
                                "relative z-10 -mb-px flex items-center border-b-2 pt-px text-sm font-medium transition-colors duration-200 ease-out"
                              )}
                            >
                              {category.name}
                            </Popover.Button>
                          </div>

                          <Transition
                            as={Fragment}
                            enter="transition ease-out duration-200"
                            enterFrom="opacity-0"
                            enterTo="opacity-100"
                            leave="transition ease-in duration-150"
                            leaveFrom="opacity-100"
                            leaveTo="opacity-0"
                          >
                            <Popover.Panel className="absolute inset-x-0 top-full text-sm text-gray-500">
                              {/* Presentational element used to render the bottom shadow, if we put the shadow on the actual panel it pokes out the top, so we use this shorter element to hide the top of the shadow */}
                              <div
                                className="absolute inset-0 top-1/2 bg-white shadow"
                                aria-hidden="true"
                              />
                              {/* Küçük açılan menü burdan başlıyor */}
                              <div className="relative bg-white z-50">
                                <div className="mx-auto max-w-7xl px-8 ">
                                  <div className="grid grid-cols-2 gap-x-8 gap-y-10 py-16 ">
                                    <div className="col-start-2 grid grid-cols-2 gap-x-8 ">
                                      {category.featured.map((item) => (
                                        <div
                                          key={item.name}
                                          className="group relative text-base sm:text-sm"
                                        >
                                          <div className="aspect-h-1 aspect-w-1 overflow-hidden rounded-lg bg-gray-100 group-hover:opacity-75">
                                            <img
                                              src={item.imageSrc}
                                              alt={item.imageAlt}
                                              className="object-cover object-center"
                                            />
                                          </div>
                                          <a
                                            href={item.href}
                                            className="mt-6 block font-medium text-gray-900"
                                          >
                                            <span
                                              className="absolute inset-0 z-10"
                                              aria-hidden="true"
                                            />
                                            {item.name}
                                          </a>
                                          <p
                                            aria-hidden="true"
                                            className="mt-1"
                                          >
                                          </p>
                                        </div>
                                      ))}
                                    </div>
                                    <div className="row-start-1 grid grid-cols-4 gap-x-8 gap-y-10 text-sm">
                                      {category.sections.map((section) => (
                                        <div key={section.name}>
                                          <p
                                            id={`${section.name}-heading`}
                                            className="font-medium text-gray-900"
                                          >
                                            {section.name}
                                          </p>
                                          <ul
                                            role="list"
                                            aria-labelledby={`${section.name}-heading`}
                                            className="mt-6 space-y-6 sm:mt-2 sm:space-y-2"
                                          >
                                            {section.items.map((item) => (
                                              <li
                                                key={item.name}
                                                className="flex"
                                              >
                                                {/* alt menülerin sayfa yönlendirmesi  */}
                                                <a
                                                  href={`/${category.id}/${section.id}/${item.value}`}
                                                  className="cursor-pointer hover:text-gray-800"
                                                >
                                                  {item.name}
                                                </a>
                                              </li>
                                            ))}
                                          </ul>
                                        </div>
                                      ))}
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </Popover.Panel>
                          </Transition>
                        </>
                      )}
                    </Popover>
                  ))}

                  {navigation.pages.map((page) => (
                    <a
                      key={page.name}
                      href={page.href}
                      className="flex items-center text-sm font-medium text-gray-700 hover:text-gray-800"
                    >
                      {page.name}
                    </a>
                  ))}
                </div>
              </Popover.Group>

              <div className="ml-auto flex items-center">
                <div className="hidden lg:flex lg:flex-1 lg:items-center lg:justify-end lg:space-x-6">
                  {auth.user?.firstName ? (
                    <div>
                      {/* Profile dropdown */}
                      <Menu as="div" className="relative ml-4 lg:ml-6">
                        <div>
                          <Menu.Button className="relative flex rounded-full bg-gray-800 text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800">
                            <Avatar
                              className="text-white"
                              onClick={handleUserClick}
                              aria-aria-controls={
                                open ? "basic-menu" : undefined
                              }
                              aria-aria-haspopup="true"
                              aria-expanded={open ? "true" : undefined}
                              sx={{
                                bgcolor: deepPurple[500],
                                color: "white",
                                cursor: "pointer",
                              }}
                            >
                              {auth.user?.firstName[0].toUpperCase()}
                            </Avatar>
                          </Menu.Button>
                        </div>
                        <Transition
                          as={Fragment}
                          enter="transition ease-out duration-100"
                          enterFrom="transform opacity-0 scale-95"
                          enterTo="transform opacity-100 scale-100"
                          leave="transition ease-in duration-75"
                          leaveFrom="transform opacity-100 scale-100"
                          leaveTo="transform opacity-0 scale-95"
                        >
                          <Menu.Items className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                            <Menu.Item>
                              {({ active }) => (
                                <a
                                  href="#"
                                  className={classNames(
                                    active ? "bg-gray-100" : "",
                                    "block px-4 py-2 text-sm text-gray-700"
                                  )}
                                >
                                  Profilim
                                </a>
                              )}
                            </Menu.Item>
                            <Menu.Item
                              onClick={() => navigate("/account/order")}
                            >
                              {({ active }) => (
                                <a
                                  href="/account/order"
                                  className={classNames(
                                    active ? "bg-gray-100" : "",
                                    "block px-4 py-2 text-sm text-gray-700"
                                  )}
                                >
                                  Siparişlerim
                                </a>
                              )}
                            </Menu.Item>
                            <Menu.Item
                              onClick={() => handleLogout()}
                            >
                              {({ active }) => (
                                <a
                                  href="/"
                                  className={classNames(
                                    active ? "bg-gray-100" : "",
                                    "block px-4 py-2 text-sm text-gray-700"
                                  )}
                                >
                                  Çıkış yap
                                </a>
                              )}
                            </Menu.Item>
                          </Menu.Items>
                        </Transition>
                      </Menu>
                    </div>
                  ) : (
                    <Button
                      onClick={handleOpen}
                      className="text-sm font-medium text-gray-700 hover:text-gray-800"
                    >
                      Giriş yap
                    </Button>
                  )}

                  <span className="h-6 w-px bg-gray-200" aria-hidden="true" />
                </div>

                <div className="hidden lg:ml-8 lg:flex">
                  <a
                    href="#"
                    className="flex items-center text-gray-700 hover:text-gray-800"
                  >
                    <img
                      src="https://upload.wikimedia.org/wikipedia/commons/b/b4/Flag_of_Turkey.svg"
                      alt=""
                      className="block h-auto w-5 flex-shrink-0"
                    />
                    <span className="ml-3 block text-sm font-medium">TR</span>
                    <span className="sr-only">, change currency</span>
                  </a>
                </div>

                {/* Search */}
                <div className="flex lg:ml-6">
                  <a href="#" className="p-2 text-gray-400 hover:text-gray-500">
                    <span className="sr-only">Search</span>
                    <MagnifyingGlassIcon
                      className="h-6 w-6"
                      aria-hidden="true"
                    />
                  </a>
                </div>

                {/* Cart */}
                <div className="ml-4 flow-root lg:ml-6">
                  <a href="#" className="group -m-2 flex items-center p-2">
                    <ShoppingBagIcon
                      className="h-6 w-6 flex-shrink-0 text-gray-400 group-hover:text-gray-500"
                      aria-hidden="true"
                    />
                    <span className="ml-2 text-sm font-medium text-gray-700 group-hover:text-gray-800">
                      0
                    </span>
                    <span className="sr-only">items in cart, view bag</span>
                  </a>
                </div>
              </div>
            </div>
          </div>
        </nav>
      </header>

      <AuthModal handleClose={handleClose} open={openAuthModel} />
    </div>
  );
}
