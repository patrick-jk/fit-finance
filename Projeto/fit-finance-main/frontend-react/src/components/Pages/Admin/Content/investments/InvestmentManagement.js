import React from 'react';
import Management from "../global/management/Management";
import {useNavigate} from "react-router-dom";

const InvestmentManagement = () => {
    let navigate = useNavigate()

    return (
        <div>
            <Management type={"investimento"} listRoute={() => navigate('/admin/investments/list-all')}
                        createRoute={() => navigate('/admin/investments/create')}
                        deleteRoute={() => navigate('/admin/investments/delete')}/>
        </div>
    )
}

export default InvestmentManagement;