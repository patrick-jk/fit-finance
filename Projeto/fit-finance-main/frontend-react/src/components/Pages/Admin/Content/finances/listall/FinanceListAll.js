import Search from "../../global/search/Search";
import {useEffect, useState} from "react";
import axios from "axios";
import {AWS_HTTP_REF, USER_TOKEN_REF} from "../../../../../../constants/constants";
import {useNavigate} from "react-router-dom";

const FinanceListAll = () => {
    const [finances, setFinances] = useState([]);
    const [search, setSearch] = useState("");
    let navigate = useNavigate();
    const goToHome = () => {
        navigate('/home');
    }

    useEffect(() => {
        axios.get(`${AWS_HTTP_REF}/finances`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(USER_TOKEN_REF)}`
            }
        })
            .then((response) => {
                setFinances(response.data);
                console.log(response.data)
            })
            .catch(error => {
                alert('Você não tem permissão para acessar essa página.');
                goToHome()
            })
    }, []);

    return (
        <div className="rootAdminDiv">
            <h1>Todas as finanças</h1>
            <div className="container">
                <Search search={search} setSearch={setSearch}/>
                <div className="row">
                    <table className="table">
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Valor</th>
                            <th scope="col">Tipo</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            finances?.filter((finance) => finance.name.toLowerCase().includes(search.toLowerCase())).map((finance) => {
                                return (
                                    <tr key={finance.id}>
                                        <td>{finance.id}</td>
                                        <td>{finance.name}</td>
                                        <td>{finance.value}</td>
                                        <td>{finance.type === 'INCOME' ? 'Renda' : 'Despesa'}</td>
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

export default FinanceListAll;