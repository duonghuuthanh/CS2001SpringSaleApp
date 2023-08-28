
import { useContext, useState } from "react";
import { Alert, Button, Form, Table } from "react-bootstrap";
import cookie from "react-cookies";
import { Link } from "react-router-dom";
import { MyCartContext, MyUserContext } from "../App";
import { authApi, endpoints } from "../configs/Apis";

const Cart = () => {
    const [, cartDispatch] = useContext(MyCartContext);
    const [user, ] = useContext(MyUserContext);
    const [carts, setCarts] = useState(cookie.load("cart") || null);

    const deleteItem = (item) => {
        cartDispatch({
            "type": "dec",
            "payload": item.quantity
        });

        if (item.id in carts) {
            setCarts(current => {
                delete current[item.id];
                cookie.save("cart", current);

                return current;
            });
        }
    }

    const updateItem = () => {
        cookie.save("cart", carts);

        cartDispatch({
            "type": "update",
            "payload": Object.values(carts).reduce((init, current) => init + current["quantity"], 0)
        })
    }

    const pay = () => {
        const process = async () => {
            let res = await authApi().post(endpoints['pay'], carts);
            if (res.status === 200) {
                cookie.remove("cart");

                cartDispatch({
                    "type": "update",
                    "payload": 0
                });

                setCarts([]);
            }
        }

        process();
    }

    if (carts === null)
        return <Alert variant="info" className="mt-2">Không có sản phẩm trong giỏ!</Alert>
    
    if (carts.length === 0)
        return <Alert variant="success" className="mt-2">Thanh toán thành công!</Alert>

    return <>
        <h1 className="text-center text-info mt-2">GIỎ HÀNG</h1>
        
        <Table striped bordered hover>
            <thead>
                <tr>
                <th>#</th>
                <th>Tên sản phẩm</th>
                <th>Đơn giá</th>
                <th>Số lượng</th>
                <th></th>
                </tr>
            </thead>
            <tbody>
                {Object.values(carts).map(c => {
                    return <tr>
                            <td>{c.id}</td>
                            <td>{c.name}</td>
                            <td>{c.unitPrice} VNĐ</td>
                            <td>
                                <Form.Control type="number" value={carts[c.id]["quantity"]} onBlur={updateItem} 
                                        onChange={e => setCarts({...carts, [c.id]: {...carts[c.id], "quantity": parseInt(e.target.value)}})}  />
                            </td>
                            <td>
                                <Button variant="danger" onClick={() => deleteItem(c)}>&times;</Button>
                            </td>
                        </tr>
                })}
                
            
            </tbody>
        </Table>

        {user===null?<p>Vui lòng <Link to="/login?next=/cart">đăng nhập</Link> để thanh toán! </p>:<Button variant="info" onClick={pay} className="mt-2 mb-2">Thanh toán</Button>}
    </>
}

export default Cart;