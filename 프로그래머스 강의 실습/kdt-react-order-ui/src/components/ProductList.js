import React from "react";
import {Product} from "./Product";

export function ProductList({products = []}) { // 일반적으로 자바스크립트 함수는 소문자인데, component 라는 것을 알려주기 위해 대문자로 시작함.
    return (
        <React.Fragment>
            <h5 className="flex-grow-0"><b>상품 목록</b></h5>
            <ul className="list-group products">
                {products.map(v =>
                    <li key={v.id} className="list-group-item d-flex mt-3">
                        <Product {...v}/>
                    </li>
                )}
            </ul>
        </React.Fragment>
    );
}