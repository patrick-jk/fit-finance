import React, {useState} from "react";
import Editar from "./editar";
import CriarItem from "./criarItem";
import './detalhesFinancas.css';
import axios from "axios";
import {USER_TOKEN_REF, AWS_HTTP_REF} from "../../../../../constants/constants";

function Detalhes({onClose, show, financeList}) {
    const [showEditar, setShowEditar] = useState(false);
    const [showCriar, setShowCriar] = useState(false);
    const [editItem, setEditItem] = useState(null);

    const handleEditar = (id, nome, valor, tipo) => {
        const financePutRequest = {
            id: id,
            name: nome,
            value: valor,
            type: tipo,
            description: "Description",
            start_date: new Date().toISOString().split('T')[0],
            end_date: new Date().toISOString().split('T')[0]
        }

        axios.put(`${AWS_HTTP_REF}/finances`, financePutRequest, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem(USER_TOKEN_REF)}`
            }
        }).then(response => {
                if (response.status === 200) {
                    console.log(`Editar item com ID ${id}, nome: ${nome}, valor: ${valor}, tipo: ${tipo}`)
                    alert("Item editado com sucesso!")
                    window.location.reload()
                }
        }).catch(error => {
            console.error(error);
            alert("Erro ao editar item " + error)
        })
    }

    const handleCriar = (nome, valor, tipo) => {
        const financePostRequest = {
            name: nome,
            value: valor,
            type: tipo,
            description: "Description",
            start_date: new Date().toISOString().split('T')[0],
            end_date: new Date().toISOString().split('T')[0]
        }

        console.log(financePostRequest)

        axios.post(`${AWS_HTTP_REF}/finances`, financePostRequest, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem(USER_TOKEN_REF)}`
            }
        })
            .then(response => {
                if (response.status === 201) {
                    console.log(`Criar novo item com nome: ${nome}, valor: ${valor}, tipo: ${tipo}`);
                    window.location.reload()
                }
            })
            .catch(error => {
                console.error(error);
                alert("Erro ao criar item " + error)
            })
    };

    const handleExcluir = (tipo, id) => {
        axios.delete(`${AWS_HTTP_REF}/finances/${id}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem(USER_TOKEN_REF)}`
            }
        }).then(response => {
            if (response.status === 204) {
                // window.location.reload()
            }
        }).catch(error => {
            console.error(error);
            alert("Erro ao excluir item " + error)
        })

        console.log(`Excluir ${tipo} com ID ${id}`);
    };

    return (
        <div className={`detalhesFinancas-overlay ${show ? "active" : ""}`} onClick={onClose}>
            <div className={`detalhesFinancas-content ${show ? "active" : ""}`} onClick={(e) => e.stopPropagation()}>
                <h2 className="detalhesTitleFinancas">Detalhes</h2>

                <button onClick={onClose} className="fecharAbaFinancas"></button>
                {editItem && <Editar show={showEditar} onClose={() => setShowEditar(false)} item={editItem}
                                     handleEditar={handleEditar}/>}
                <div className="table-createbtn">
                    <table className="tabela-detalhesFinancas table">
                        <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Tipo</th>
                            <th>Valor</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        {financeList?.map((finance) => (
                            <tr key={finance.id}>
                                <td>{finance.name}</td>
                                <td>{finance.type}</td>
                                <td>{finance.value}</td>
                                <td>
                                    <div className="dropdown">
                                        <button className="dropbtn">⋮</button>
                                        <div className="dropdown-content">
                                            <a href="#editar" className="botao-editar" onClick={(e) => {
                                                e.preventDefault();
                                                setEditItem(finance);
                                                setShowEditar(true);
                                            }}>Editar</a>
                                            <a href="#excluir" className="botao-excluir" onClick={(e) => {
                                                e.preventDefault();
                                                handleExcluir(finance.type, finance.id);
                                            }}>Excluir</a>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    <button className="botao-criar" onClick={(e) => {
                        e.preventDefault();
                        setShowCriar(true);
                    }}>Novo
                    </button>
                    {showCriar &&
                        <CriarItem show={showCriar} onClose={() => setShowCriar(false)} handleCriar={handleCriar}/>}
                </div>
            </div>
        </div>
    );
}
 
export default Detalhes;
