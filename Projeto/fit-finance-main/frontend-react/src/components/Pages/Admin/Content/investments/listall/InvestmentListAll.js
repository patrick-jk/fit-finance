import Search from "../../global/search/Search";
import {useEffect, useState} from "react";
import axios from "axios";
import {AWS_HTTP_REF, USER_TOKEN_REF} from "../../../../../../constants/constants";
import {useNavigate} from "react-router-dom";

const InvestmentListAll = () => {
    const [investments, setInvestments] = useState([]);
    const [search, setSearch] = useState("");
    let navigate = useNavigate();
    const goToHome = () => {
        navigate('/home');
    }

    const convertEnum = (type) => {
        switch (type) {
            case "STOCK":
                return "Ação";
            case "FII":
                return "FII";
            case "FIXED_INCOME":
                return "Renda Fixa";
        }
    };

    useEffect(() => {
        axios.get(`${AWS_HTTP_REF}/investments`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(USER_TOKEN_REF)}`
            }
        }).then((response) => {
            setInvestments(response.data);
        }).catch(error => {
            alert('Você não tem permissão para acessar essa página.');
            goToHome()
        })
    }, []);

    return (
        <div className="rootAdminDiv">
            <h1>Todos os investimentos</h1>
            <div className="container">
                <Search search={search} setSearch={setSearch}/>
                <div className="row">
                    <table className="table">
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Quantidade</th>
                            <th scope="col">Preço</th>
                            <th scope="col">Tipo</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            investments.filter((investment) => investment.name.toLowerCase().includes(search.toLowerCase())).map((investment) => {
                                return (
                                    <tr key={investment.id}>
                                        <td>{investment.id}</td>
                                        <td>{investment.name}</td>
                                        <td>{investment.quantity}</td>
                                        <td>{investment.price}</td>
                                        <td>{convertEnum(investment.type)}</td>
                                    </tr>
                                )
                            })
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}

export default InvestmentListAll;