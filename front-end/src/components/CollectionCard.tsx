import { useNavigate } from 'react-router-dom'
import { MovieType } from '@/Interface/movie'
import { convertMintuteToHour, getDay } from '@/utils'
import { LazyLoadImage } from 'react-lazy-load-image-component'
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger
} from '@/components/ui/alert-dialog'

export const CollectionCard = ({
  className,
  movie
}: {
  className?: string
  movie: MovieType
}) => {
  const navigate = useNavigate()
  const { slug, name, image, rate, categoryId, fromDate, duration, age_limit } =
    movie
  const categorySection = categoryId?.map((category, index) => {
    return (
      <span className="mr-2" key={index}>
        {category.name}
        {index === categoryId.length - 1 ? '' : ','}
      </span>
    )
  })

  return (
    <div
      className={`home-movie-card bg-background-card ${className} shadow-lg dark:shadow-2xl`}
    >
      <div className="home-movie-img-box">
        <LazyLoadImage
          className="movie-info-img"
          src={image}
          alt={'Movie Photo'}
          effect="opacity"
        />
      </div>

      <div className="movie-card-line line-1">
        <p className="movie-title text-primary-cardMovie xs:text-xl">{name}</p>

        <div className="movie-rating text-primary-infoMovie">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="collection-icon text-primary-movieColor fill-primary-movieColor"
            viewBox="0 0 512 512"
          >
            <path d="M394 480a16 16 0 01-9.39-3L256 383.76 127.39 477a16 16 0 01-24.55-18.08L153 310.35 23 221.2a16 16 0 019-29.2h160.38l48.4-148.95a16 16 0 0130.44 0l48.4 149H480a16 16 0 019.05 29.2L359 310.35l50.13 148.53A16 16 0 01394 480z" />
          </svg>
          <span>{rate}/5</span>
        </div>
      </div>

      <p className="movie-genre text-primary-infoMovie">{categorySection}</p>

      <div className="movie-card-third-line">
        <div className="line-2">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="collection-icon text-primary-movieColor fill-primary-movieColor"
            viewBox="0 0 512 512"
          >
            <rect
              fill="none"
              stroke="currentColor"
              strokeLinejoin="round"
              strokeWidth="32"
              x="48"
              y="80"
              width="416"
              height="384"
              rx="48"
            />
            <circle cx="296" cy="232" r="24" />
            <circle cx="376" cy="232" r="24" />
            <circle cx="296" cy="312" r="24" />
            <circle cx="376" cy="312" r="24" />
            <circle cx="136" cy="312" r="24" />
            <circle cx="216" cy="312" r="24" />
            <circle cx="136" cy="392" r="24" />
            <circle cx="216" cy="392" r="24" />
            <circle cx="296" cy="392" r="24" />
            <path
              fill="none"
              stroke="currentColor"
              strokeLinejoin="round"
              strokeWidth="32"
              strokeLinecap="round"
              d="M128 48v32M384 48v32"
            />
            <path
              fill="none"
              stroke="currentColor"
              strokeLinejoin="round"
              strokeWidth="32"
              d="M464 160H48"
            />
          </svg>
          <p className="category">{getDay(fromDate)}</p>
        </div>

        <div className="line-3">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="collection-icon text-primary-movieColor fill-primary-movieColor"
            viewBox="0 0 512 512"
          >
            <path
              d="M256 64C150 64 64 150 64 256s86 192 192 192 192-86 192-192S362 64 256 64z"
              fill="none"
              stroke="currentColor"
              strokeMiterlimit="10"
              strokeWidth="32"
            />
            <path
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="32"
              d="M256 128v144h96"
            />
          </svg>
          <p className="category-value">{convertMintuteToHour(duration)}</p>
        </div>
      </div>

      <AlertDialog>
        <AlertDialogTrigger asChild>
          <button className="book-btn bg-primary-movieColor hover:bg-primary-movieColorSecond btn">
            Đặt vé
          </button>
        </AlertDialogTrigger>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle className="text-3xl mb-4 mt-2">
              Xác nhận mua vé?
            </AlertDialogTitle>
            <AlertDialogDescription className="text-2xl">
              Phim này chỉ dành cho trẻ em trên {age_limit} tuổi. Vui lòng cân
              nhắc khi mua vé. BQL Rạp sẽ phải từ chối cho vào nếu sai quy định.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel className="text-2xl px-9 py-3">
              Hủy
            </AlertDialogCancel>
            <AlertDialogAction
              onClick={() => {
                navigate('/movie/' + slug)
              }}
              className="bg-primary-movieColor text-2xl px-9 py-3"
            >
              Tiếp tục
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  )
}
