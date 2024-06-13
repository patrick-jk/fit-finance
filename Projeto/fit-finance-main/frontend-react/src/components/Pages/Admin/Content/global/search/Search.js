import React from "react";
import './Search.css';

const Search = ({ search, setSearch }) => {
    return (
        <div className="search">
            <input className="inputField" placeholder="Pesquisar..."
                   type="text"
                   value={search}
                   onChange={(e) => setSearch(e.target.value)}
            />
        </div>
    );
}

export default Search;