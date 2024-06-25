import React, {useState, useEffect} from "react";
import Chart from "react-apexcharts";
import "./HomeLogged.css";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {USER_TOKEN_REF, AWS_HTTP_REF} from "../../../constants/constants";

const HomeLogged = () => {
    const [userSummary, setUserSummary] = useState(null);
    const [hiddenChart, setHiddenChart] = useState(true);

    let navigate = useNavigate();
    const goToFinances = () => {
        navigate("/financas");
    };
    const goToInvestments = () => {
        navigate("/investimentos");
    };

    useEffect(() => {
        axios.get(`${AWS_HTTP_REF}/finances/user-summary`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(USER_TOKEN_REF)}`,
            },
        })
            .then((response) => {
                if (response.status === 200) {
                    console.log(response.data);
                    setUserSummary(response.data);
                    setHiddenChart(false);
                }
            })
            .catch((error) => {
                console.log(error);
            });
    }, []);

    const state = userSummary ? {
        series: [userSummary.totalExpenses, userSummary.balance],
        options: {
            colors: ["#00c49a", "#19a2fd"],
            chart: {
                foreColor: "#ffffff",
                type: "pie",
                fontSize: "18px",
            },
            stroke: {
                width: 0
            },
            labels: ["Saldo Usado", "Saldo Restante"],
            dataLabels: {
                enabled: true,
                style: {
                    fontSize: "20px"
                },
            },
            legend: {
                position: "bottom",
                fontSize: "18px",
            },
            responsive: [
                {
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200,
                        },
                    },
                },
            ],
        },
    } : null;

    return (
        <div className="rootHomeLogged-div">
            <div className="title-div">
                <h1>
                    Olá,{" "}
                    {localStorage.getItem("user-name") !== null
                        ? localStorage.getItem("user-name")
                        : "usuário"}
                    !
                </h1>
                <p>Seja bem-vindo ao nosso sistema de finanças pessoais.</p>
            </div>
            <div className="contentHomeLogged">
                <div className="pie-Saldo col-md-5 align-self-center">
                    {hiddenChart ? <h2 className="noDataHome">Não há dados.</h2> :
                        <Chart
                            className="chart-HomeLogged"
                            options={state.options}
                            series={state.series}
                            type="pie"
                        />
                    }
                </div>

                <hr className="line-div"></hr>

                <div className="container-homeLogged text-center col-md-5 align-self-center">
                    {userSummary && (
                        <div>
                            <div className="row-HomeLogged-principal">
                                <div className="contentGridSaldo">
                                    <h3>Saldo Projetado:</h3>
                                    <p>
                                        {userSummary.balance < 0
                                            ? "Você está no vermelho..."
                                            : "Excelente, finanças em Dia!"}
                                    </p>
                                    <p>
                                        {userSummary.balance >= 0 ? "R$ " + userSummary.balance : "-R$ " + userSummary.balance}
                                    </p>
                                    <p style={{fontSize: 12}}>(Salário Base + Rendas - Despesas)</p>
                                </div>
                            </div>
                            <div className="row-HomeLogged">
                                <div className="col-HomeLogged-Plus">
                                    <div className="contentGrid">
                                        <h5>Maior Despesa</h5>
                                        <p>{userSummary.biggestExpense.name}</p>
                                        <button className="btn-homeLogged-values" onClick={goToFinances}>
                                            Finanças
                                        </button>
                                    </div>
                                </div>
                                <div className="col-HomeLogged-Minus">
                                    <div className="contentGrid">
                                        <h5>Menor Despesa</h5>
                                        <p>{userSummary.smallestExpense.name}</p>
                                        <button className="btn-homeLogged-values" onClick={goToFinances}>
                                            Finanças
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div className="row-HomeLogged">
                                <div className="col-HomeLogged-Plus">
                                    <div className="contentGrid">
                                        <h5>Maior Investimento</h5>
                                        <p>{userSummary.biggestInvestment.name}</p>
                                        <button className="btn-homeLogged-values" onClick={goToInvestments}>
                                            Investimentos
                                        </button>
                                    </div>
                                </div>
                                <div className="col-HomeLogged-Minus">
                                    <div className="contentGrid">
                                        <h5>Menor Investimento</h5>
                                        <p>{userSummary.smallestInvestment.name}</p>
                                        <button className="btn-homeLogged-values" onClick={goToInvestments}>
                                            Investimentos
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default HomeLogged;