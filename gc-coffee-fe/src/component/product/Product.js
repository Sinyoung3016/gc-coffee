import React from "react";

export function Product(props) {
    const handleAddBtnClicked = () => {
        props.onAddClick(props.productId);
    }
    return <>
        <div className="col-2">
            <img className="img-fluid" src="https://i.imgur.com/HKOFQYa.jpeg" alt=""/>
        </div>
        <div className="col">
            <div className="row text-muted">{props.category}</div>
            <div className="row">{props.productName}</div>
        </div>
        <div className="col text-center price">{props.price}</div>
        <div className="col text-end action">
            <button className="btn btn-small btn-outline-dark" onClick={handleAddBtnClicked}>추가</button>
        </div>
    </>
}