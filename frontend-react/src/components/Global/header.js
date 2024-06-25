import React, { useState, useEffect, useRef } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import "./header.css";
import axios from "axios";
import {
  TOKEN_EXPIRATION_REF,
  USER_TOKEN_REF,
  AWS_HTTP_REF,
} from "../../constants/constants";

function Header() {
  let navigate = useNavigate();
  const location = useLocation();
  const isAuthenticated =
    localStorage.getItem(USER_TOKEN_REF) !== null &&
    parseInt(localStorage.getItem(TOKEN_EXPIRATION_REF)) >
      new Date().getMilliseconds();

  const operacao_Logout = "/auth/logout";
  const requestHTTP = AWS_HTTP_REF + operacao_Logout;

  const [isCollapsed, setIsCollapsed] = useState(true);
  const navRef = useRef(null);

  const handleLogout = () => {
    if (!window.confirm("Deseja realmente sair?")) return;

    axios
      .post(
        requestHTTP,
        {},
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem(USER_TOKEN_REF),
          },
        }
      )
      .then((response) => {
        if (response.status === 200) {
          localStorage.removeItem("user-token");
          localStorage.removeItem("token-expiry-date");
          localStorage.removeItem("user-name");

          alert("Logout efetuado com sucesso");

          navigate("/");
        }
      })
      .catch((error) => {
        alert("Erro ao fazer logout " + error);
      });
  };

  const changeHomeRoute = () => {
    return isAuthenticated ? "/home" : "/";
  };

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth >= 992) {
        setIsCollapsed(true);
      }
    };

    const handleClickOutside = (event) => {
      if (navRef.current && !navRef.current.contains(event.target)) {
        setIsCollapsed(true);
      }
    };

    window.addEventListener("resize", handleResize);
    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      window.removeEventListener("resize", handleResize);
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <header id="headerChart">
      <div className="container-fluid">
        <nav className="navbar navbar-expand-lg navbar-dark justify-content-between fixed-top" ref={navRef}>
          <Link className="navbar-logo" to={changeHomeRoute()}>
            <img
              src="/images/header_logo.svg"
              alt="Header Logo"
              className="img-fluid"
            />
          </Link>
          <button
            className={`navbar-toggler ${
              isCollapsed ? "collapsed" : "expanded"
            }`}
            type="button"
            aria-controls="navbarNav"
            aria-expanded={!isCollapsed}
            aria-label="Toggle navigation"
            onClick={() => setIsCollapsed(!isCollapsed)}
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div
            className={`collapse navbar-collapse ${isCollapsed ? "" : "show"}`}
            id="navbarNav"
          >
            <ul className="navbar-nav ms-auto mb-lg-0">
              <li className="nav-item">
                <Link
                  className={`nav-link ${
                    location.pathname === "/home" ? "active" : ""
                  }`}
                  to={changeHomeRoute()}
                >
                  Home
                </Link>
              </li>
              <li className="nav-item">
                <Link
                  className={`nav-link ${
                    location.pathname === "/financas" ? "active" : ""
                  }`}
                  to="/financas"
                >
                  Finanças
                </Link>
              </li>
              <li className="nav-item">
                <Link
                  className={`nav-link ${
                    location.pathname === "/investimentos" ? "active" : ""
                  }`}
                  to="/investimentos"
                >
                  Carteira
                </Link>
              </li>
              <li>
                <div
                  className={`nav-line ${isCollapsed ? "" : "d-none"}`}
                ></div>
              </li>
              <li className="nav-item">
                {isAuthenticated ? (
                  <a className="nav-link" onClick={handleLogout}>
                    Logout
                  </a>
                ) : (
                  <Link
                    className={`nav-link ${
                      location.pathname === "/login" ? "active" : ""
                    }`}
                    to="/login"
                  >
                    Login
                  </Link>
                )}
              </li>
            </ul>
          </div>
        </nav>
      </div>
    </header>
  );
}

export default Header;
