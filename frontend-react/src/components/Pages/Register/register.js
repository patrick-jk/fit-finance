import React, { useState } from 'react';
import './register.css';
import MinimalHeader from '../../Global/minimalHeader';
import { Container, Form, Row, Modal, Button } from "react-bootstrap";
import InputComponent from "./components/InputComponent";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { AWS_HTTP_REF } from "../../../constants/constants";

const Registrar = () => {
    const [name, setName] = useState('');
    const [cpf, setCpf] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [birthdate, setBirthdate] = useState('');
    const [phone, setPhone] = useState('');
    const [income, setIncome] = useState('');

    const [showSuccessModal, setShowSuccessModal] = useState(false);
    const [showErrorModal, setShowErrorModal] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    let navigate = useNavigate();
    const goToLogin = () => {
        navigate('/login');
    }

    const handleSubmit = (event) => {
        event.preventDefault()

        const registerRequest = {
            name: name,
            cpf: cpf,
            email: email,
            password: password,
            birthdate: birthdate,
            phone: phone,
            income: income
        }

        axios.post(`${AWS_HTTP_REF}/auth/register`, registerRequest)
            .then(response => {
                if (response.status === 201) {
                    localStorage.setItem('user-name', response.data.name)
                    setShowSuccessModal(true);
                }
            })
            .catch(error => {
                setErrorMessage(error.message || 'Erro desconhecido');
                setShowErrorModal(true);
            });
    }

    const handleCpf = (event) => {
        let value = event.target.value;
        let cpfPattern = value.replace(/\D/g, '') // Remove qualquer coisa que não seja número
            .replace(/(\d{3})(\d)/, '$1.$2') // Adiciona ponto após o terceiro dígito
            .replace(/(\d{3})(\d)/, '$1.$2') // Adiciona ponto após o sexto dígito
            .replace(/(\d{3})(\d)/, '$1-$2') // Adiciona traço após o nono dígito
            .replace(/(-\d{2})\d+?$/, '$1'); // Impede entrada de mais de 11 dígitos
        setCpf(cpfPattern);
    }

    const handlePhone = (event) => {
        let value = event.target.value;
        let phonePattern = value.replace(/\D/g, '') // Remove qualquer coisa que não seja número
            .replace(/(\d{2})(\d)/, '($1) $2') // Adiciona parênteses após o segundo dígito
            .replace(/(\d{5})(\d)/, '$1-$2') // Adiciona traço após o quinto dígito
            .replace(/(-\d{4})\d+?$/, '$1'); // Impede entrada de mais de 11 dígitos
        setPhone(phonePattern);
    }

    return (
        <div className="bodyValue">
            <MinimalHeader />
            <Container className="containerCenter">
                <div className="floatregister">
                    <div className="register-box">
                        <h1 className="titleRegister">Registrar</h1>
                        <Form onSubmit={handleSubmit}>

                            <div>
                                <Row className="row-margin">
                                    <InputComponent label="Nome" inputType="text" value={name}
                                                    onChange={(event) => setName(event.target.value)} />

                                    <InputComponent label="CPF" inputType="text" value={cpf}
                                                    onChange={handleCpf} />
                                </Row>
                                <Row className="row-margin">
                                    <InputComponent label="Email" inputType="email" value={email}
                                                    onChange={(event) => setEmail(event.target.value)} />

                                    <InputComponent label="Senha" inputType="password" value={password}
                                                    onChange={(event) => setPassword(event.target.value)} />
                                </Row>
                                <Row className="row-margin">
                                    <InputComponent label="Nascimento" inputType="date" value={birthdate}
                                                    onChange={(event) => setBirthdate(event.target.value)} />

                                    <InputComponent label="Telefone" inputType="tel" value={phone}
                                                    onChange={handlePhone} />
                                </Row>
                            </div>

                            <div className="inputRenda">
                                <Row className="row-margin">
                                    <p>Renda Mensal</p>
                                    <div className="input-group mb-3">
                                        <span className="input-group-text">R$</span>
                                        <input type="text" className="form-control" value={income}
                                               onChange={(e) => setIncome(e.target.value)}/>
                                        <span className="input-group-text">.00</span>
                                    </div>
                                </Row>
                            </div>

                            <Row className="row-margin">
                                <div className="containerCenter">
                                    <button className="btn-register" type="submit">Registrar</button>
                                </div>
                            </Row>

                        </Form>
                    </div>
                </div>
            </Container>

            <Modal show={showSuccessModal} onHide={() => setShowSuccessModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title className="modal-text-black">Sucesso</Modal.Title>
                </Modal.Header>
                <Modal.Body className="modal-text-black">
                    Usuário registrado com sucesso!
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={() => { setShowSuccessModal(false); goToLogin(); }}>
                        OK
                    </Button>
                </Modal.Footer>
            </Modal>

            <Modal show={showErrorModal} onHide={() => setShowErrorModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title className="modal-text-black">Erro</Modal.Title>
                </Modal.Header>
                <Modal.Body className="modal-text-black">
                    Erro ao registrar usuário: {errorMessage}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowErrorModal(false)}>
                        Fechar
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default Registrar;
