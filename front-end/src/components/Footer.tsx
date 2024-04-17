// import { useEffect, useState } from 'react'
// import axios from 'axios'
import { Link } from 'react-router-dom'
import { HashLink } from 'react-router-hash-link'
// import HashLoader from 'react-spinners/HashLoader'

export const Footer = () => {
  return (
    <section className="section-footer container">
      {/* {pageName === 'home' ? (
        <HashLink className="footer-logo-container" to="#headerTop">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="footer-logo-icon"
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
          <h1 className="footer-logo-text">Asho Dekhi</h1>
        </HashLink>
      ) : (
        <Link className="footer-logo-container" to="/">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="footer-logo-icon"
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
          <h1 className="footer-logo-text">Asho Dekhi</h1>
        </Link>
      )} */}
      <HashLink className="footer-logo-container" to="#headerTop">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="footer-logo-icon"
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
        <h1 className="footer-logo-text text-primary-locationMovie">
        DREAM CINEMA
        </h1>
      </HashLink>

      <div className="footer-link-container foot-reg">
        <button className="footer-btn hover:text-primary-movieColorSecond text-foreground">
          Tạo tài khoản 
        </button>
      </div>

      <div className="footer-link-container">
        <button className="footer-btn hover:text-primary-movieColorSecond text-foreground">
         Đăng nhập 
        </button>
      </div>

      <div className="footer-link-container">
        <Link
          className="footer-link text-foreground hover:text-primary-movieColorSecond"
          to="/aboutus"
        >
          Về chúng tôi
        </Link>
      </div>

      <h3 className="footer-heading">Rạp phim  của chúng tôi</h3>

      <p className="copyright text-primary-copyright">
      Bản quyền &copy; 2024 bởi Envidi, Inc. Mọi quyền được bảo lưu.
      </p>

      <div className="footer-address-container">
        {/* {loading ? <HashLoader color="#eb3656" /> : locations} */}
        HA NOI
      </div>
    </section>
  )
}
