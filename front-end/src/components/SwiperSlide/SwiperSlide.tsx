 import { checkSlidePerView } from '@/utils'
import { Swiper } from 'swiper/react'
import 'swiper/css/pagination'
import 'swiper/css'
// import 'swiper/css/pagination'
import 'swiper/css/navigation'
import './slide.css'
import { Navigation } from 'swiper/modules'
import { FreeMode } from 'swiper/modules'
import { Pagination } from 'swiper/modules'
import 'swiper/css/free-mode'
import { MovieType } from '@/Interface/movie'

interface SwiperDataType {
  dataMovie: MovieType[]
  slidePerView: number
  children: React.ReactNode
}

function SwiperSlider({ dataMovie, slidePerView, children }: SwiperDataType) {
  return (
    <Swiper
      slidesPerView={slidePerView}
      spaceBetween={30}
      freeMode={true}
      pagination={{
        clickable: true
      }}
      navigation={true}
      breakpoints={{
        // when window width is >= 320px
        350: {
          slidesPerView: 1,
          spaceBetween: 1
        },
        500: {
          slidesPerView: 2,
          spaceBetween: 20
        },
        // when window width is >= 480px
        600: {
          slidesPerView: checkSlidePerView(dataMovie, 2),
          spaceBetween: 20
        },
        // when window width is >= 600px
        730: {
          slidesPerView: checkSlidePerView(dataMovie, 3),
          spaceBetween: 10
        },
        950: {
          slidesPerView: checkSlidePerView(dataMovie, 4),
          spaceBetween: 20
        },
        // when window width is >= 640px
        1024: {
          slidesPerView: checkSlidePerView(dataMovie, 4),
          spaceBetween: 40
        },
        // when window width is >= 640px
        1240: {
          slidesPerView: checkSlidePerView(dataMovie, slidePerView),
          spaceBetween: 15
        },
        // when window width is >= 640px
        1280: {
          slidesPerView: checkSlidePerView(dataMovie, slidePerView),
          spaceBetween: 10
        }
      }}
      modules={[FreeMode, Navigation, Pagination]}
      className="mySwiper ps-2 xs:py-5 sm:py-0 sm:block xs:hidden"
      // onSlideChange={setSwiperRef}
    >
      {children}
    </Swiper>
  )
}

export default SwiperSlider
