import { useSwipeable } from 'react-swipeable'

import React, { useState } from 'react'
import { ChevronLeft } from 'lucide-react'
import { ChevronRight } from 'lucide-react'

import './CardCarousel.css'
// const CARDS = 10
const MAX_VISIBILITY = 3

export const Card = ({
  title,
  content
}: {
  title: string
  content: string
}) => (
  <div className="card">
    <h2>{title}</h2>
    <p>{content}</p>
  </div>
)

const Carousel = ({
  children,
  className
}: {
  children: React.ReactNode
  className: string
}) => {
  const [active, setActive] = useState(2)
  const count = React.Children.count(children)

  const handlers = useSwipeable({
    onSwipedLeft: () => setActive((i) => i + 1),
    onSwipedRight: () => setActive((i) => i - 1),
    swipeDuration: 500,
    preventScrollOnSwipe: true,
    trackMouse: true
  })

  return (
    <div className={`carousel ${className} hover:cursor-pointer`} {...handlers}>
      {active > 0 && (
        <button
          className="nav left opacity-55 rounded-full p-5 bg-background-btnControllerCarousel"
          onClick={() => setActive((i) => i - 1)}
        >
          <ChevronLeft size={24} />
        </button>
      )}
      {React.Children.map(children, (child: React.ReactNode, i: number) => (
        <div
          //   {...handlers}
          className="card-container"
          style={
            {
              '--active': i === active ? 1 : 0,
              '--offset': (active - i) / 3,
              '--direction': Math.sign(active - i),
              '--abs-offset': Math.abs(active - i) / 3,
              pointerEvents: active === i ? 'auto' : 'none',
              opacity: Math.abs(active - i) >= MAX_VISIBILITY ? '0' : '1',
              display: Math.abs(active - i) > MAX_VISIBILITY ? 'none' : 'block'
            } as never
          }
        >
          {child}
        </div>
      ))}
      {active < count - 1 && (
        <button
          className="nav right opacity-55 rounded-full p-5 bg-background-btnControllerCarousel"
          onClick={() => setActive((i) => i + 1)}
        >
          <ChevronRight size={24} />
        </button>
      )}
    </div>
  )
}
export default Carousel

// const App = () => (
//   <div className="app">
//     <Carousel>
//       {[...new Array(CARDS)].map((_, i) => (
//         <Card
//           title={'Card ' + (i + 1)}
//           content="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
//         />
//       ))}
//     </Carousel>
//   </div>
// )

// ReactDOM.render(<App />, document.body)
