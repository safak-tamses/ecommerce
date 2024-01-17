export const color = [
  "Red",
  "Blue",
  "Green",
  "Yellow",
  "Purple",
  "Orange",
  "Pink",
];

export const filters = [
  {
    id: "color",
    name: "Renk",
    options: [
      { value: "KIRMIZI", label: "Kırmızı" },
      { value: "MAVI", label: "Mavi" },
      { value: "YESIL", label: "Yeşil" },
      { value: "BEYAZ", label: "Beyaz" },
      { value: "SIYAH", label: "Siyah" },
      { value: "TURUNCU", label: "Turuncu" },
      { value: "PEMBE", label: "Pempe" },
    ],
  },
  {
    id: "size",
    name: "Beden",
    options: [
      { value: "SMALL", label: "Small" },
      { value: "MEDIUM", label: "Medium" },
      { value: "LARGE", label: "Large" },
    ],
  },
];

export const singleFilter = [
  {
    id: "price",
    name: "Fiyat",
    options: [
      { value: "0-450", label: "0 ile 450 TL arası" },
      { value: "450-1250", label: "450 - 1250 TL arası" },
      { value: "1250-2000", label: "1250 - 2000 TL arası" },
      { value: "2000-5000", label: "2000 - 5000 TL arası" },
      { value: "5000-9000", label: "5000 - 9000 TL arası" },
      { value: "9000-999999", label: "9000 TL üstü" },
    ],
  },
];

export const sortOptions = [
  { name: "Önerilen sıralama", query: "", current: false },
  { name: "En düşük fiyat", query: "price_low", current: false },
  { name: "En yüksek fiyat", query: "price_high", current: false },
];
