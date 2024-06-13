import React from 'react';
import './UserManagement.css';
import Management from "../global/management/Management";
import {useNavigate} from "react-router-dom";

const UserManagement = () => {
    let navigate = useNavigate()

    return (
        <div className="rootUserManagementDiv">
            <Management type="usuário" listRoute={() => navigate('/admin/users/list-all')}
                        createRoute={() => navigate('/admin/users/create')} deleteRoute={() => navigate('/admin/users/delete')}/>
        </div>
    )
}

export default UserManagement;