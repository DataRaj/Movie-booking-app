import { ReactElement } from 'react'
interface MyActionObject {
  handleClick: () => void
  type: ReactElement
  className: string
}
function Action({ handleClick, type, className }: MyActionObject) {
  return (
    <div className={className} onClick={handleClick}>
      {type}
    </div>
  )
}

export default Action
