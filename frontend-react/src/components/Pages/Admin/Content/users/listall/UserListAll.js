import Search from "../../global/search/Search";
import {useEffect, useState} from "react";
import axios from "axios";
import {AWS_HTTP_REF, USER_TOKEN_REF} from "../../../../../../constants/constants";
import {useNavigate} from "react-router-dom";

const UserListAll = () => {
    const [users, setUsers] = useState([]);
    const [search, setSearch] = useState("");
    let navigate = useNavigate();
    const goToHome = () => {
        navigate('/home');
    }

    useEffect(() => {
        axios.get(`${AWS_HTTP_REF}/users`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(USER_TOKEN_REF)}`
            }
        })
            .then((response) => {
                setUsers(response.data);
            })
            .catch(error => {
                alert('Você não tem permissão para acessar essa página.');
                goToHome()
            })
    }, []);

    return (
        <div className="rootAdminDiv">
            <h1>Todos os usuários</h1>
            <div className="container">
                <Search search={search} setSearch={setSearch}/>
                <div className="row">
                    <table className="table">
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Email</th>
                            <th scope="col">Salário</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            users.filter((user) => user.name.toLowerCase().includes(search.toLowerCase())).map((user) => {
                                return (
                                    <tr key={user.id}>
                                        <td>{user.id}</td>
                                        <td>{user.name}</td>
                                        <td>{user.email}</td>
                                        <td>{user.income}</td>
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

export default UserListAll;