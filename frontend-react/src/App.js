import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import Header from './components/Global/header';
import Home from './components/Pages/Home/home';
import Finances from './components/Pages/Finances/homeFinances';
import Investimentos from './components/Pages/Investimentos/investimentos';
import Login from './components/Pages/Login/login';
import './App.css';
import Register from "./components/Pages/Register/register";
import PrivateWrapper from "./routes/PrivateWrapper";
import HomeLogged from "./components/Pages/HomeLogged/HomeLogged";
import Admin from "./components/Pages/Admin/Admin";
import UserManagement from "./components/Pages/Admin/Content/users/UserManagement";
import InvestmentManagement from "./components/Pages/Admin/Content/investments/InvestmentManagement";
import FinanceManagement from "./components/Pages/Admin/Content/finances/FinanceManagement";
import FinanceListAll from "./components/Pages/Admin/Content/finances/listall/FinanceListAll";
import UserListAll from "./components/Pages/Admin/Content/users/listall/UserListAll";
import InvestmentListAll from "./components/Pages/Admin/Content/investments/listall/InvestmentListAll";

function App() {
    return (
        <Router>
            <div>
                <Header/>
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route element={<PrivateWrapper/>}>
                        <Route path="/home" element={<HomeLogged/>}/>
                    </Route>
                    <Route element={<PrivateWrapper/>}>
                        <Route path="/financas" element={<Finances/>}/>
                    </Route>
                    <Route element={<PrivateWrapper/>}>
                        <Route path="/investimentos" element={<Investimentos/>}/>
                    </Route>
                    <Route element={<PrivateWrapper/>}>
                        <Route path="/admin" element={<Admin/>}/>
                        <Route path="/admin/users" element={<UserManagement/>}/>
                        <Route path="/admin/users/list-all" element={<UserListAll/>}/>
                        <Route path="/admin/users/create" element={<UserManagement/>}/>
                        <Route path="/admin/users/delete" element={<UserManagement/>}/>
                        <Route path="/admin/investments" element={<InvestmentManagement/>}/>
                        <Route path="/admin/investments/list-all" element={<InvestmentListAll/>}/>
                        <Route path="/admin/investments/create" element={<InvestmentManagement/>}/>
                        <Route path="/admin/investments/delete" element={<InvestmentManagement/>}/>
                        <Route path="/admin/finances" element={<FinanceManagement/>}/>
                        <Route path="/admin/finances/list-all" element={<FinanceListAll/>}/>
                        <Route path="/admin/finances/create" element={<FinanceManagement/>}/>
                        <Route path="/admin/finances/delete" element={<FinanceManagement/>}/>
                    </Route>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/register" element={<Register/>}/>
                </Routes>
            </div>
        </Router>
    );
}

export default App;
