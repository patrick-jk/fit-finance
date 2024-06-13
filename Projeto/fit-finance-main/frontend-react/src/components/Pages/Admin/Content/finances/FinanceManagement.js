import React from 'react';
import Management from "../global/management/Management";
import {useNavigate} from "react-router-dom";

const FinanceManagement = () => {
    let navigate = useNavigate()

    return (
        <div>
            <Management type={"finança"} listRoute={() => navigate('/admin/finances/list-all')}
                        createRoute={() => navigate('/admin/finances/create')}
                        deleteRoute={() => navigate('/admin/finances/delete')}/>
        </div>
    )
}

export default FinanceManagement;