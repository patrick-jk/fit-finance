import React, {useState} from "react";
import './login.css';
import MinimalHeader from "../../Global/minimalHeader";
import {Link, useNavigate} from "react-router-dom";
import {Form} from "react-bootstrap";
import axios from "axios";
import InputComponent from "../Register/components/InputComponent";
import {TOKEN_EXPIRATION_REF, USER_TOKEN_REF, AWS_HTTP_REF} from "../../../constants/constants";

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    let navigate = useNavigate();
    const goToHome = () => {
        navigate('/home');
    }

    const handleSubmit = (event) => {
        event.preventDefault()

        const authenticationRequest = {
            email: email,
            password: password
        }

        axios.post(`${AWS_HTTP_REF}/auth/authenticate`, authenticationRequest)
            .then(response => {
                if (response.status === 200) {
                    localStorage.setItem(USER_TOKEN_REF, response.data.access_token)
                    localStorage.setItem(TOKEN_EXPIRATION_REF, (new Date().getMilliseconds() + 86400000).toString())
                    console.log(new Date().toISOString())
                    console.log(response.data.access_token)
                    alert('Usuário logado com sucesso!')
                    goToHome()
                }
            }).catch(error => {
                alert('Erro ao logar usuário: ' + error)
            });
    }

    return (
        <div className="bodyValue">
            <MinimalHeader/>
            <div className="float-login">
                <div className="login-box">
                    <p className="title">Entrar</p>

                    <Form onSubmit={handleSubmit} className="form">
                        <div className="textboxLogin">
                            <InputComponent label="Email" inputType="tel" value={email}
                                            onChange={(event) => setEmail(event.target.value)}/>
                        </div>
                        <div className="textboxLogin">
                            <InputComponent label="Senha" inputType="password" value={password}
                                            onChange={(event) => setPassword(event.target.value)}/>
                        </div>
                        <div className="register">
                            <p>Não Cadastrado?&nbsp;<Link to="/register">Clique aqui</Link></p>
                        </div>
                        <button type="submit" className="btn-login">Login</button>
                    </Form>
                </div>
            </div>
        </div>
    )
}

export default Login;