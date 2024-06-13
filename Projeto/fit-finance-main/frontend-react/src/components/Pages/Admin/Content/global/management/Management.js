import PropTypes from "prop-types";
import React from "react";
import './Management.css';

const Management = ({type, listRoute, createRoute, deleteRoute}) => {
    return (
        <div className="rootAdminDiv">
            <h1>{type.charAt(0).toUpperCase() + type.slice(1, type.length)}s</h1>
            <div className="container managementContainer">
                <div className="row">
                    <button className="btn btn-primary" onClick={listRoute}>Listar {type}s</button>
                </div>
                <div className="row">
                    <button className="btn btn-primary" onClick={createRoute}>Adicionar {type}</button>
                </div>
                <div className="row">
                    <button className="btn btn-primary" onClick={deleteRoute}>Remover {type}</button>
                </div>
            </div>
        </div>
    )
}

Management.propTypes = {
    type: PropTypes.string.isRequired
}

export default Management;