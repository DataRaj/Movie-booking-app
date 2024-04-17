// import { useState } from 'react'
import { Link } from 'react-router-dom'
import { HashLink } from 'react-router-hash-link'
import SearchBar from './SearchBar/SearchBar'
import { useContext, useState } from 'react'
import { SignupModal } from '@/pages/modals/SignupModal'
import { ContextAuth, ContextMain } from '@/context/Context'
import { LoginModal } from '@/pages/modals/LoginModal'
// import { MdLogout } from 'react-icons/md'
import DropDownMenu from './DropDownMenu'
import { Bell } from 'lucide-react'
import { Separator } from './ui/separator'
import TooltipComponent from './TooltipComponent'

// eslint-disable-next-line no-unused-vars
export const Navbar = ({
  setMenuState
}: {
  // eslint-disable-next-line no-unused-vars
  setMenuState: (state: (prevState: boolean) => boolean) => boolean
}) => {
  const [showSignup, setShowSignup] = useState(false)
  const [showSignIn, setShowSignIn] = useState(false)
  const [showNav, setShowNav] = useState(false)
  // const [showProfile, setShowProfile] = useState(false)
  // const [signUpState, setSignUpState] = useState(false)
  const { isLogined } = useContext<ContextAuth>(ContextMain)
  const toggleShowForm = () => {
    setShowSignup((pre) => !pre)
  }
  const toggleShowNav = () => {
    setShowNav((pre) => !pre)
  }
  // const toggleShowProfile = () => {
  //   setShowProfile((pre) => !pre)
  // }

  const toggleShowFormSignIn = () => {
    setShowSignIn((pre) => !pre)
  }
  

  return (
    <>
      <header>
        <div>
          <button
            className="btn-menu"
            onClick={() => setMenuState((prevState: boolean) => !prevState)}
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="menu-icon"
              viewBox="0 0 512 512"
            >
              <path
                fill="none"
                stroke="currentColor"
                strokeLinecap="round"
                strokeMiterlimit="10"
                strokeWidth="32"
                d="M80 160h352M80 256h352M80 352h352"
              />
            </svg>
          </button>

          <HashLink className="logo-container" to="#headerTop">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="main-logo-icon"
              viewBox="0 0 512 512"
            >
              <path
                d="M448 256c0-106-86-192-192-192S64 150 64 256s86 192 192 192 192-86 192-192z"
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
                d="M360 94.59V296M443.13 212.87L296 360M417.41 360H216M299.13 443.13l-144-144M152 416V216M68.87 299.13l144-144M94.59 152H288M212.87 68.87L360 216"
              />
            </svg>
            <h1 className="logo-text ">Dream Cinema</h1>
          </HashLink>
        </div>

        <nav>
          <ul className="nav-items">
            <li>
              <Link className="nav-item" to="/">
                Trang chủ
              </Link>
            </li>
            <li>
              <Link className="nav-item" to="/showtimes">
                Lịch chiếu
              </Link>
            </li>
            <li>
              <Link className="nav-item" to="/movies">
                Phim
              </Link>
            </li>
            <li>
              <Link className="nav-item" to="/aboutus">
                Quy định
              </Link>
            </li>

            <li className="relative">
              <SearchBar />
            </li>
          </ul>
        </nav>

        <div className="nav-signup">
          <div className="relative">
            <TooltipComponent tooltip={'Thông báo'}>
              <div>
                <Bell size={20} className="text-primary-locationMovie" />
                <span className="w-7 h-7 rounded-full flex justify-center items-center  bg-primary-movieColor absolute top-[-8px] right-[-5px]">
                  2
                </span>
              </div>
            </TooltipComponent>
          </div>
          <Separator
            className="bg-border-borderSocialLink h-9 ms-8 mr-4"
            orientation="vertical"
          />
          {isLogined ? (
            <>
              <DropDownMenu  />
              {/* <MdLogout className="text-2xl cursor-pointer " onClick={logout} /> */}
            </>
          ) : (
            <>
              <button className="customer-profile-btn">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="profile-icon"
                  viewBox="0 0 512 512"
                >
                  <path
                    d="M344 144c-3.92 52.87-44 96-88 96s-84.15-43.12-88-96c-4-55 35-96 88-96s92 42 88 96z"
                    fill="none"
                    stroke="currentColor"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="32"
                  />
                  <path
                    d="M256 304c-87 0-175.3 48-191.64 138.6C62.39 453.52 68.57 464 80 464h352c11.44 0 17.62-10.48 15.65-21.4C431.3 352 343 304 256 304z"
                    fill="none"
                    stroke="currentColor"
                    strokeMiterlimit="10"
                    strokeWidth="32"
                  />
                </svg>
              </button>
              <div>
                <button className="btn-signup-arrow" onClick={toggleShowNav}>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    className="signup-icon"
                    viewBox="0 0 512 512"
                  >
                    <path
                      fill="none"
                      stroke="currentColor"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="48"
                      d="M112 184l144 144 144-144"
                    />
                  </svg>
                </button>

                {showNav && (
                  <div className="signup-options">
                    {
                      <ul className="signup-buttons">
                        <li>
                          <button
                            className="signup-button"
                            onClick={toggleShowForm}
                          >
                            Đăng Ký
                          </button>
                        </li>
                        <li>
                          <button
                            className="login-button"
                            onClick={toggleShowFormSignIn}
                          >
                            Đăng Nhập
                          </button>
                        </li>
                      </ul>
                    }
                  </div>
                )}
              </div>
            </>
          )}
        </div>
      </header>

      {showSignup && <SignupModal />}

      {showSignIn && <LoginModal />}
    </>
  )
}
