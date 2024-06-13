import React, { useState, useEffect } from "react";
import Chart from "react-apexcharts";
import "./HomeLogged.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { USER_TOKEN_REF, AWS_HTTP_REF } from "../../../constants/constants";

const HomeLogged = () => {
    const [saldoUsado, setSaldoUsado] = useState(0);
    const [saldoRestante, setSaldoRestante] = useState(0);
    const [biggestExpense, setBiggestExpense] = useState(null);
    const [smallestExpense, setSmallestExpense] = useState(null);
    const [biggestInvestment, setBiggestInvestment] = useState(null);
    const [smallestInvestment, setSmallestInvestment] = useState(null);
    const [hiddenChart, setHiddenChart] = useState(false);

    let navigate = useNavigate();
    const goToFinances = () => {
        navigate("/financas");
    };
    const goToInvestments = () => {
        navigate("/investimentos");
    };

    useEffect(() => {
        axios.get(`${AWS_HTTP_REF}/finances/by-user-id`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(USER_TOKEN_REF)}`,
            },
        })
        .then((response) => {
            if (response.status === 200) {
                console.log('finances', response.data);
                let accExpense = 0;
                let accIncome = 0;
                let localBiggestExpense = { name: null, value: Number.MIN_VALUE };
                let localSmallestExpense = { name: null, value: Number.MAX_VALUE };

                response.data.forEach((finance) => {
                    if (finance.type === 'EXPENSE') {
                        accExpense += finance.value;
                        if (finance.value > localBiggestExpense.value) {
                            localBiggestExpense = { name: finance.name, value: finance.value };
                        }
                        if (finance.value < localSmallestExpense.value) {
                            localSmallestExpense = { name: finance.name, value: finance.value };
                        }
                    } else {
                        accIncome += finance.value;
                    }
                });

                console.log('Total Expenses:', accExpense);
                console.log('Total Income:', accIncome);

                setSaldoUsado(accExpense);
                const userIncome = response.data[0]?.user.income || 0;
                const projectedSaldoRestante = userIncome + accIncome - accExpense;
                setSaldoRestante(projectedSaldoRestante);
                setBiggestExpense(localBiggestExpense.name);
                setSmallestExpense(localSmallestExpense.name);

                setHiddenChart(projectedSaldoRestante <= 0 && accExpense <= 0);
                
                console.log('Projected Saldo Restante:', projectedSaldoRestante);
                console.log('User Income:', userIncome);
            }
        })
        .catch((error) => {
            console.log(error);
        });

        axios.get(`${AWS_HTTP_REF}/investments/by-user-id`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem(USER_TOKEN_REF)}`,
            },
        })
        .then((response) => {
            if (response.status === 200) {
                console.log('investments', response.data);
                let localBiggestInvestment = { name: null, value: Number.MIN_VALUE };
                let localSmallestInvestment = { name: null, value: Number.MAX_VALUE };

                response.data.forEach((investment) => {
                    const investmentValue = investment.price * investment.quantity;
                    if (investmentValue > localBiggestInvestment.value) {
                        localBiggestInvestment = { name: investment.name, value: investmentValue };
                    }
                    if (investmentValue < localSmallestInvestment.value) {
                        localSmallestInvestment = { name: investment.name, value: investmentValue };
                    }
                });

                setBiggestInvestment(localBiggestInvestment.name);
                setSmallestInvestment(localSmallestInvestment.name);
            }
        })
        .catch((error) => {
            console.log(error);
        });
    }, []);

    const state = {
        series: [saldoUsado, saldoRestante],
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
    };

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
                    <div className="row-HomeLogged-principal">
                        <div className="contentGridSaldo">
                            <h3>Saldo Projetado:</h3>
                            <p>
                                {saldoRestante < 0
                                    ? "Você está no vermelho..."
                                    : "Excelente, finanças em Dia!"}
                            </p>
                            <p>
                                {saldoRestante >= 0 ? "R$ " + saldoRestante : "-R$ " + saldoRestante}
                            </p>
                            <p style={{fontSize: 12}}>(Salário Base + Rendas - Despesas)</p>
                        </div>
                    </div>
                    <div className="row-HomeLogged">
                        <div className="col-HomeLogged-Plus">
                            <div className="contentGrid">
                                <h5>Maior Despesa</h5>
                                <p>{biggestExpense}</p>
                                <button className="btn-homeLogged-values" onClick={goToFinances}>
                                    Finanças
                                </button>
                            </div>
                        </div>
                        <div className="col-HomeLogged-Minus">
                            <div className="contentGrid">
                                <h5>Menor Despesa</h5>
                                <p>{smallestExpense}</p>
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
                                <p>{biggestInvestment}</p>
                                <button className="btn-homeLogged-values" onClick={goToInvestments}>
                                    Investimentos
                                </button>
                            </div>
                        </div>
                        <div className="col-HomeLogged-Minus">
                            <div className="contentGrid">
                                <h5>Menor Investimento</h5>
                                <p>{smallestInvestment}</p>
                                <button className="btn-homeLogged-values" onClick={goToInvestments}>
                                    Investimentos
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default HomeLogged;