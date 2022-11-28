import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import React, {useState, useEffect} from 'react';
import {Summary} from "./component/summary/Summary";
import {ProductList} from "./component/product/ProductList";
import axios from "axios";

function App() {

    useEffect(() => {
        axios.get("http://localhost:8080/api/v1/products")
            .then(v => setProducts(v.data));
    }, []);

    const [products, setProducts] = useState([]);
    const [items, setItems] = useState([]);
    const handleAddBtnClicked = id => {
        const product = products.find(v => v.productId === id);
        const found = items.find(v => v.productId === id);
        const updatedItem = found ? items.map(v => (v.productId === id) ? {
            ...v,
            count: v.count + 1
        } : v) : [...items, {...product, count: 1}]
        setItems(updatedItem);
    };
    const handleOrderSubmit = order => {
        if (items.length === 0) {
            alert("아이템을 추가해주세요.");
        } else {

            axios.post("http://localhost:8080/api/v1/orders", {
                email: order.email,
                address: order.address,
                postcode: order.postcode,
                orderItems:
                    items.map(v => ({
                        productId: v.productId,
                        category: v.category,
                        price: v.price,
                        quantity: v.count,
                    }))
            }).then(v => alert("주문이 정상적으로 접수되었습니다."),
                e => {
                    alert("서버 장애")
                    console.log(e)
                })
        }
    }
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
                    <Summary items={items} onOrderSubmit={handleOrderSubmit}/>
                </div>
            </div>
        </div>
        </body>
    );
}

export default App;
