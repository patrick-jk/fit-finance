import React, {useEffect, useState} from 'react';
import './Admin.css';
import axios from "axios";
import {AWS_HTTP_REF} from "../../../constants/constants";
import {Link, Navigate, Outlet, useNavigate} from "react-router-dom";

const Admin = () => {
    const [users, setUsers] = useState([])
    const [investments, setInvestments] = useState([])
    const [finances, setFinances] = useState([])

    let navigate = useNavigate();
    const goToUsers = () => {
        navigate('/admin/users');
    }
    const goToInvestments = () => {
        navigate('/admin/investments');
    }
    const goToFinances = () => {
        navigate('/admin/finances');
    }

    useEffect(() => {
    }, []);

    return (
        <div className="rootAdminDiv">
            <h1>Admin</h1>
            <div className="container adminContainer">
                <div className="row">
                    <button className="btn btn-primary" onClick={goToUsers}>Usuários</button>
                </div>
                <div className="row">
                    <button className="btn btn-primary" onClick={goToInvestments}>Investimentos</button>
                </div>
                <div className="row">
                    <button className="btn btn-primary" onClick={goToFinances}>Finanças</button>
                </div>
            </div>
        </div>
    )
}

export default Admin;