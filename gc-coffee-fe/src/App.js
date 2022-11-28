import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import React, {useState, useEffect} from 'react';
import {Summary} from "./component/summary/Summary";
import {ProductList} from "./component/product/ProductList";
import axios from "axios";

function App() {

    useEffect(()=>{
        axios.get("http://localhost:8080/api/v1/products")
            .then(v => setProducts(v.data));
    }, []);

    const [products, setProducts] = useState([
        {id: "uuid1", productName: "colombia1", category: "coffee bean", price: 1000},
        {id: "uuid2", productName: "colombia2", category: "coffee bean", price: 2000},
        {id: "uuid3", productName: "colombia3", category: "coffee bean", price: 3000}
    ]);
    const [items, setItems] = useState([]);
    const handleAddBtnClicked = id => {
        const product = products.find(v => v.id === id);
        const found = items.find(v => v.id === id);
        const updatedItem = found ? items.map(v => (v.id === id) ? {...v, count: v.count + 1} : v) : [...items, {...product, count: 1}]
        setItems(updatedItem);
    };
    return (
        <body>
        <div className="row justify-content-center m-4">
            <h1 className="text-center">Grids & Circle</h1>
        </div>
        <div className="card">
            <div className="row">
                <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                    <ProductList products={products} onAddClick={handleAddBtnClicked}/>
                </div>
                <div className="col-md-4 summary p-4">
                    <Summary items={items}/>
                </div>
            </div>
        </div>
        </body>
    );
}

export default App;
