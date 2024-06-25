import React, { useState } from "react";
import './login.css';
import MinimalHeader from "../../Global/minimalHeader";
import { Link, useNavigate } from "react-router-dom";
import { Form, Modal, Button } from "react-bootstrap";
import axios from "axios";
import InputComponent from "../Register/components/InputComponent";
import { TOKEN_EXPIRATION_REF, USER_TOKEN_REF, AWS_HTTP_REF } from "../../../constants/constants";

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const [showSuccessModal, setShowSuccessModal] = useState(false);
    const [showErrorModal, setShowErrorModal] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    let navigate = useNavigate();
    const goToHome = () => {
        navigate('/home');
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        const authenticationRequest = {
            email: email,
            password: password
        };

        axios.post(`${AWS_HTTP_REF}/auth/authenticate`, authenticationRequest)
            .then(response => {
                if (response.status === 200) {
                    localStorage.setItem(USER_TOKEN_REF, response.data.access_token);
                    localStorage.setItem(TOKEN_EXPIRATION_REF, (new Date().getMilliseconds() + 86400000).toString());
                    setShowSuccessModal(true);
                }
            })
            .catch(error => {
                setErrorMessage(error.message || 'Erro desconhecido');
                setShowErrorModal(true);
            });
    }

    return (
        <div className="bodyValue">
            <MinimalHeader />
            <div className="float-login">
                <div className="login-box">
                    <p className="title">Entrar</p>

                    <Form onSubmit={handleSubmit} className="form">
                        <div className="textboxLogin">
                            <InputComponent label="Email" inputType="email" value={email}
                                            onChange={(event) => setEmail(event.target.value)} />
                        </div>
                        <div className="textboxLogin">
                            <InputComponent label="Senha" inputType="password" value={password}
                                            onChange={(event) => setPassword(event.target.value)} />
                        </div>
                        <div className="register">
                            <p>Não Cadastrado?&nbsp;<Link to="/register">Clique aqui</Link></p>
                        </div>
                        <button type="submit" className="btn-login">Login</button>
                    </Form>
                </div>
            </div>

            {/* Modal de Sucesso */}
            <Modal show={showSuccessModal} onHide={() => setShowSuccessModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title className="modal-text-black">Sucesso</Modal.Title>
                </Modal.Header>
                <Modal.Body className="modal-text-black">
                    Usuário logado com sucesso!
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={() => { setShowSuccessModal(false); goToHome(); }}>
                        OK
                    </Button>
                </Modal.Footer>
            </Modal>

            {/* Modal de Erro */}
            <Modal show={showErrorModal} onHide={() => setShowErrorModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title className="modal-text-black">Erro</Modal.Title>
                </Modal.Header>
                <Modal.Body className="modal-text-black">
                    Erro ao logar usuário: {errorMessage}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowErrorModal(false)}>
                        Fechar
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}

export default Login;
