import React, {useEffect, useState} from 'react';
import Header from "../../Global/header";
import Detalhes from "./Content/details/detalhes";
import Container from "./Content/container/Container";
import './style.css';
import axios from "axios";
import {USER_TOKEN_REF, AWS_HTTP_REF} from "../../../constants/constants";
import login from "../Login/login";

const Finances = () => {
    const [showDetalhes, setShowDetalhes] = useState(false);
    const [finances, setFinances] = useState([]);

    useEffect(() => {
        axios.get(`${AWS_HTTP_REF}/finances/by-user-id`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(USER_TOKEN_REF)}`
            }
        }).then((response) => {
            if (response.status === 200) {
                setFinances(response.data)
            }
        }).catch((error) => {
            console.log('Erro ao buscar finanças ' + error)
        })
    }, []);

    const handleVerDetalhesClick = () => {
        setShowDetalhes(true);
    };

    const handleFecharDetalhes = () => {
        setShowDetalhes(false);
    };


    return (
        <div id="page-content">
            <Header/>
            <div className="container">
                    <h2 className="financesTitle">Suas Finanças</h2>
                </div>
            <div className="page-content">
                <Container handleSeeDetailsClick={handleVerDetalhesClick} financeList={finances}/>
                <Detalhes onClose={handleFecharDetalhes} show={showDetalhes} financeList={finances}/>
            </div>
        </div>
    );
}

export default Finances;
